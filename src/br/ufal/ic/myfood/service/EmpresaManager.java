package br.ufal.ic.myfood.service;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.repositories.EmpresaRepository;
import br.ufal.ic.myfood.repositories.EmpresaRepositoryImpl;
import br.ufal.ic.myfood.validators.EmpresaValidator;

import java.util.List;

public class EmpresaManager {

    private EmpresaRepository empresaRepository;
    private EmpresaValidator empresaValidator;
    private ProdutoManager produtoManager;

    public EmpresaManager() {
        this(new ProdutoManager());
    }

    public EmpresaManager(ProdutoManager produtoManager) {
        this.empresaRepository = new EmpresaRepositoryImpl();
        this.empresaValidator = new EmpresaValidator();
        this.produtoManager = produtoManager;
        
        this.produtoManager.setEmpresaRepository(new ProdutoManager.EmpresaRepository() {
            @Override
            public Empresa obterPorId(int id) {
                return empresaRepository.obterPorId(id);
            }
        });
    }

    public int criarEmpresa(String tipoEmpresa, String idDono, String nome, String endereco, String tipoCozinha, String nomeDono)
            throws EmpresaJaExisteException, EmpresaDuplicadaException, DadosInvalidosException {

        empresaValidator.validarTipoEmpresa(tipoEmpresa);
        empresaValidator.validarNome(nome);
        empresaValidator.validarEndereco(endereco);

        for (Empresa empresa : empresaRepository.obterTodas()) {
            if (empresa.getNome().equals(nome) && !empresa.getIdDono().equals(idDono)) {
                throw new EmpresaJaExisteException();
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco) && empresa.getIdDono().equals(idDono)) {
                throw new EmpresaDuplicadaException();
            }
        }

        Empresa empresa = new Empresa(0, nome, endereco, tipoCozinha, idDono, nomeDono);
        empresa.setTipo("restaurante");
        empresaRepository.adicionar(empresa);

        return empresa.getId();
    }

    public int criarMercado(String tipoEmpresa, String idDono, String nome, String endereco, String abre, String fecha, String tipoMercado, String nomeDono)
            throws EmpresaJaExisteException, EmpresaDuplicadaException, DadosInvalidosException {

        empresaValidator.validarTipoEmpresa(tipoEmpresa);
        empresaValidator.validarNome(nome);
        empresaValidator.validarEndereco(endereco);
        empresaValidator.validarHorarioParaCriar(abre, fecha);
        empresaValidator.validarTipoMercado(tipoMercado);

        for (Empresa empresa : empresaRepository.obterTodas()) {
            if (empresa.getNome().equals(nome) && !empresa.getIdDono().equals(idDono)) {
                throw new EmpresaJaExisteException();
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco) && empresa.getIdDono().equals(idDono)) {
                throw new EmpresaDuplicadaException();
            }
        }

        Empresa empresa = new Empresa(0, nome, endereco, "mercado", abre, fecha, tipoMercado, idDono, nomeDono);
        empresaRepository.adicionar(empresa);

        return empresa.getId();
    }

    public String getEmpresasDoUsuario(String idDono, List<Usuario> usuarioList)
            throws UsuarioNaoPodeCriarEmpresaException {
        
        Usuario dono = null;
        for (Usuario usuario : usuarioList) {
            if (usuario.getId().equals(idDono)) {
                dono = usuario;
                break;
            }
        }

        if (dono == null || dono.getCpf() == null) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("{[");
        boolean primeiro = true;
        for (Empresa empresa : empresaRepository.obterPorDono(idDono)) {
            if (!primeiro) resultado.append(", ");
            resultado.append("[").append(empresa.getNome()).append(", ").append(empresa.getEndereco()).append("]");
            primeiro = false;
        }
        resultado.append("]}");

        return resultado.toString();
    }

    public String getAtributoEmpresa(int idEmpresa, String atributo) 
            throws EmpresaNaoCadastradaException, AtributoInvalidoException {
        
        Empresa empresa = empresaRepository.obterPorId(idEmpresa);
        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        }

        empresaValidator.validarAtributo(atributo);

        switch(atributo.toLowerCase()) {
            case "nome":
                return empresa.getNome();
            case "endereco":
                return empresa.getEndereco();
            case "tipocozinha":
                return empresa.getTipoCozinha();
            case "dono":
                return empresa.getNomeDono();
            case "tipomercado":
                return empresa.getTipoMercado();
            case "abre":
                return empresa.getAbre();
            case "fecha":
                return empresa.getFecha();
            case "aberto24horas":
                Boolean aberto = empresa.getAberto24Horas();
                return aberto == null ? "false" : (aberto ? "true" : "false");
            case "numerofuncionarios":
                return String.valueOf(empresa.getNumeroFuncionarios());
            default:
                throw new AtributoInvalidoException();
        }
    }

    public int criarFarmacia(String tipoEmpresa, String idDono, String nome, String endereco, Boolean aberto24Horas, int numeroFuncionarios, String nomeDono)
            throws EmpresaJaExisteException, EmpresaDuplicadaException, DadosInvalidosException {

        empresaValidator.validarTipoEmpresa(tipoEmpresa);
        empresaValidator.validarNome(nome);
        empresaValidator.validarEndereco(endereco);
        empresaValidator.validarNumeroFuncionarios(numeroFuncionarios);

        for (Empresa empresa : empresaRepository.obterTodas()) {
            if (empresa.getNome().equals(nome) && !empresa.getIdDono().equals(idDono)) {
                throw new EmpresaJaExisteException();
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco) && empresa.getIdDono().equals(idDono)) {
                throw new EmpresaDuplicadaException();
            }
        }

        Empresa empresa = new Empresa(0, nome, endereco, "farmacia", aberto24Horas, numeroFuncionarios, idDono, nomeDono);
        empresaRepository.adicionar(empresa);

        return empresa.getId();
    }

    public int getIdEmpresa(String idDono, String nome, int indice)
            throws NomeInvalidoException, IndiceInvalidoException, IndiceForaDoIntervaroException, EmpresaNaoEncontradaNomeException {
        
        empresaValidator.validarNome(nome);
        empresaValidator.validarIndice(indice);

        List<Empresa> empresasComNome = obterEmpresasComNomeEDono(nome, idDono);

        if (empresasComNome.isEmpty()) {
            throw new EmpresaNaoEncontradaNomeException();
        }

        if (indice >= empresasComNome.size()) {
            throw new IndiceForaDoIntervaroException();
        }

        return empresasComNome.get(indice).getId();
    }

    private List<Empresa> obterEmpresasComNomeEDono(String nome, String idDono) {
        List<Empresa> empresas = empresaRepository.obterPorDono(idDono);
        empresas.removeIf(e -> !e.getNome().equals(nome));
        return empresas;
    }

    public void zerarDados() {
        empresaRepository.limpar();
        produtoManager.zerarDados();
    }

    public String listarProdutos(int empresaId)
            throws EmpresaNaoExisteException {
        Empresa empresa = empresaRepository.obterPorId(empresaId);
        if (empresa == null) {
            throw new EmpresaNaoExisteException();
        }
        return produtoManager.listarProdutos(empresaId);
    }

    public Empresa getEmpresaById(int empresaId) {
        return empresaRepository.obterPorId(empresaId);
    }

    public void atualizarEmpresa(Empresa empresa) {
        empresaRepository.atualizar(empresa);
    }

    public java.util.List<Empresa> obterTodasEmpresas() {
        return empresaRepository.obterTodas();
    }

    public void alterarFuncionamento(int mercadoId, String abre, String fecha)
            throws NaoEhMercadoValidoException, DadosInvalidosException {

        Empresa mercado = empresaRepository.obterPorId(mercadoId);

        if (mercado == null || !mercado.getTipo().equals("mercado")) {
            throw new NaoEhMercadoValidoException();
        }

        empresaValidator.validarHorario(abre, fecha);

        mercado.setAbre(abre);
        mercado.setFecha(fecha);
    }
}
