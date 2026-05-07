package br.ufal.ic.myfood;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.models.Pedido;
import br.ufal.ic.myfood.service.*;
import br.ufal.ic.myfood.persistence.PersistenceManager;

public class Facade {

    private static final String NO_CPF = "NO_CPF";
    private static UsuarioManager userManager;
    private static EmpresaManager empresaManager;
    private static ProdutoManager produtoManager;
    private static PedidoManager pedidoManager;
    private static br.ufal.ic.myfood.service.EntregaManager entregaManager;

    public Facade() {
        if (userManager == null) {
            userManager = new UsuarioManager();
        }
        if (produtoManager == null) {
            produtoManager = new ProdutoManager();
        }
        if (empresaManager == null) {
            empresaManager = new EmpresaManager(produtoManager);
        }
        if (pedidoManager == null) {
            pedidoManager = new PedidoManager();
            setupPedidoManagerDependencies();
        }
        if (entregaManager == null) {
            entregaManager = new EntregaManager(pedidoManager, userManager, empresaManager);
        }
    }

    private void setupPedidoManagerDependencies() {
        pedidoManager.setUsuarioRepository(new PedidoManager.UsuarioRepository() {
            @Override
            public Usuario obterPorId(String id) {
                return userManager.getUsuarioById(id);
            }
        });

        pedidoManager.setEmpresaRepository(new PedidoManager.EmpresaRepository() {
            @Override
            public Empresa obterPorId(int id) {
                try {
                    return empresaManager.getEmpresaById(id);
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public Usuario getDonoEmpresa(int id) {
                try {
                    Empresa empresa = empresaManager.getEmpresaById(id);
                    if (empresa != null) {
                        return userManager.getUsuarioById(empresa.getIdDono());
                    }
                } catch (Exception e) {
                }
                return null;
            }
        });

        pedidoManager.setProdutoRepository(new PedidoManager.ProdutoRepository() {
            @Override
            public Produto obterPorId(int id) {
                return produtoManager.obterProdutoPorId(id);
            }

            @Override
            public Produto obterPorIdEEmpresa(int id, int empresaId) {
                return produtoManager.obterProdutoPorIdEEmpresa(id, empresaId);
            }
        });
    }

    public void zerarSistema() {
        userManager.zerarDados();
        empresaManager.zerarDados();
        pedidoManager.zerarDados();
        entregaManager.zerarDados();
        PersistenceManager.limparTodosDados();
    }

    public void liberarPedido(int numero) throws DadosInvalidosException {
        pedidoManager.liberarPedido(numero);
    }

    public int obterPedido(String entregador) throws DadosInvalidosException, UsuarioNaoExisteException {
        Usuario usuario = userManager.getUsuarioById(entregador);
        if (usuario == null) throw new UsuarioNaoExisteException();

        if (usuario.getPlaca() == null || usuario.getPlaca().isEmpty() || usuario.getVeiculo() == null || usuario.getVeiculo().isEmpty()) {
            throw new UsuarioNaoEEntregadorException();
        }

        java.util.List<Integer> empresasQueTrabalha = new java.util.ArrayList<>();
        for (Empresa emp : empresaManager.obterTodasEmpresas()) {
            if (emp.getEntregadores().contains(entregador)) {
                empresasQueTrabalha.add(emp.getId());
            }
        }

        if (empresasQueTrabalha.isEmpty()) {
            throw new EntregadorSemEmpresaException();
        }

        java.util.List<Pedido> pedidosProntos = new java.util.ArrayList<>();
        for (Pedido p : pedidoManager.obterTodosPedidos()) {
            if ("pronto".equals(p.getEstado())) {
                int empId = Integer.parseInt(p.getEmpresa());
                if (empresasQueTrabalha.contains(empId)) {
                    pedidosProntos.add(p);
                }
            }
        }

        if (pedidosProntos.isEmpty()) {
            throw new NaoExistePedidoParaEntregaException();
        }

        Pedido escolhido = null;
        for (Pedido p : pedidosProntos) {
            Empresa emp = empresaManager.getEmpresaById(Integer.parseInt(p.getEmpresa()));
            if (emp != null && "farmacia".equals(emp.getTipo())) {
                if (escolhido == null || p.getNumero() < escolhido.getNumero()) escolhido = p;
            }
        }
        if (escolhido == null) {
            for (Pedido p : pedidosProntos) {
                if (escolhido == null || p.getNumero() < escolhido.getNumero()) escolhido = p;
            }
        }

        if (escolhido == null) {
            throw new NaoExistePedidoParaEntregaException();
        }

        return escolhido.getNumero();
    }

    public int criarEntrega(int pedido, String entregador, String destino) throws DadosInvalidosException {
        return entregaManager.criarEntrega(pedido, entregador, destino);
    }

    public String getEntrega(int id, String atributo) throws DadosInvalidosException {
        return entregaManager.getEntrega(id, atributo);
    }

    public int getIdEntrega(int pedido) throws DadosInvalidosException {
        return entregaManager.getIdEntregaByPedido(pedido);
    }

    public void entregar(int entrega) throws DadosInvalidosException {
        entregaManager.entregar(entrega);
    }

    public String getAtributoUsuario(String id, String atributo) throws UsuarioNaoExisteException {
        return userManager.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws UsuarioJaExisteException, DadosInvalidosException {
        criarUsuario(nome, email, senha, endereco, NO_CPF);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws UsuarioJaExisteException, DadosInvalidosException {
        if (NO_CPF.equals(cpf)) {
            cpf = null;
        } else if (cpf == null) {
            cpf = "";
        }
        userManager.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa)
            throws UsuarioJaExisteException, DadosInvalidosException {
        userManager.criarUsuario(nome, email, senha, endereco, veiculo, placa);
    }

    public String login(String email, String senha) throws LoginInvalidoException {
        return userManager.login(email, senha);
    }

    public int criarEmpresa(String tipoEmpresa, String idDono, String nome, String endereco, String tipoCozinha)
            throws EmpresaJaExisteException, EmpresaDuplicadaException, UsuarioNaoPodeCriarEmpresaException, DadosInvalidosException, UsuarioNaoExisteException {

        Usuario dono = userManager.getUsuarioById(idDono);
        if (dono == null) {
            throw new UsuarioNaoExisteException();
        }

        if (dono.getCpf() == null) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }

        return empresaManager.criarEmpresa(tipoEmpresa, idDono, nome, endereco, tipoCozinha, dono.getNome());
    }

    public int criarEmpresa(String tipoEmpresa, String idDono, String nome, String endereco, String abre, String fecha, String tipoMercado)
            throws EmpresaJaExisteException, EmpresaDuplicadaException, UsuarioNaoPodeCriarEmpresaException, DadosInvalidosException, UsuarioNaoExisteException {

        Usuario dono = userManager.getUsuarioById(idDono);
        if (dono == null) {
            throw new UsuarioNaoExisteException();
        }

        if (dono.getCpf() == null) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }

        return empresaManager.criarMercado(tipoEmpresa, idDono, nome, endereco, abre, fecha, tipoMercado, dono.getNome());
    }

    public int criarEmpresa(String tipoEmpresa, String idDono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios)
            throws EmpresaJaExisteException, EmpresaDuplicadaException, UsuarioNaoPodeCriarEmpresaException, DadosInvalidosException, UsuarioNaoExisteException {

        Usuario dono = userManager.getUsuarioById(idDono);
        if (dono == null) {
            throw new UsuarioNaoExisteException();
        }

        if (dono.getCpf() == null) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }

        return empresaManager.criarFarmacia(tipoEmpresa, idDono, nome, endereco, aberto24Horas, numeroFuncionarios, dono.getNome());
    }

    public String getEmpresasDoUsuario(String idDono)
            throws UsuarioNaoPodeCriarEmpresaException {
        return empresaManager.getEmpresasDoUsuario(idDono, userManager.getUsuarios());
    }

    public String getAtributoEmpresa(int empresa, String atributo)
            throws EmpresaNaoCadastradaException, AtributoInvalidoException {
        return empresaManager.getAtributoEmpresa(empresa, atributo);
    }

    public int getIdEmpresa(String idDono, String nome, int indice)
            throws NomeInvalidoException, IndiceInvalidoException, IndiceForaDoIntervaroException, EmpresaNaoEncontradaNomeException {
        return empresaManager.getIdEmpresa(idDono, nome, indice);
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria)
            throws DadosInvalidosException, EmpresaNaoExisteException {
        return produtoManager.criarProduto(empresa, nome, valor, categoria);
    }

    public void cadastrarEntregador(int empresa, String entregador) throws DadosInvalidosException, EmpresaNaoExisteException, UsuarioNaoExisteException {
        Empresa emp = empresaManager.getEmpresaById(empresa);
        if (emp == null) {
            throw new EmpresaNaoExisteException();
        }

        Usuario usuario = userManager.getUsuarioById(entregador);
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        }

        if (usuario.getPlaca() == null || usuario.getPlaca().isEmpty() || usuario.getVeiculo() == null || usuario.getVeiculo().isEmpty()) {
            throw new UsuarioNaoEEntregadorException();
        }


        emp.adicionarEntregador(usuario.getId());
        empresaManager.atualizarEmpresa(emp);
    }

    public String getEntregadores(int empresa) throws EmpresaNaoExisteException {
        Empresa emp = empresaManager.getEmpresaById(empresa);
        if (emp == null) {
            throw new EmpresaNaoExisteException();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{[");
        boolean first = true;
        for (String uid : emp.getEntregadores()) {
            Usuario u = userManager.getUsuarioById(uid);
            String email = u == null ? "" : u.getEmail();
            if (!first) sb.append(", ");
            sb.append(email);
            first = false;
        }
        sb.append("]}");
        return sb.toString();
    }

    public String getEmpresas(String entregador) throws DadosInvalidosException, UsuarioNaoExisteException {
        Usuario usuario = userManager.getUsuarioById(entregador);
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        }

        if (usuario.getPlaca() == null || usuario.getPlaca().isEmpty() || usuario.getVeiculo() == null || usuario.getVeiculo().isEmpty()) {
            throw new UsuarioNaoEEntregadorException();
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("{[");
        boolean primeiro = true;
        for (Empresa empresa : empresaManager.obterTodasEmpresas()) {
            if (empresa.getEntregadores().contains(entregador)) {
                if (!primeiro) resultado.append(", ");
                resultado.append("[").append(empresa.getNome()).append(", ").append(empresa.getEndereco()).append("]");
                primeiro = false;
            }
        }
        resultado.append("]}");
        return resultado.toString();
    }

    public void editarProduto(int produto, String nome, float valor, String categoria)
            throws DadosInvalidosException {
        produtoManager.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo)
            throws DadosInvalidosException, EmpresaNaoExisteException {
        return produtoManager.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa)
            throws EmpresaNaoExisteException {
        return empresaManager.listarProdutos(empresa);
    }

    public int criarPedido(String cliente, int empresa)
            throws DadosInvalidosException {
        return pedidoManager.criarPedido(cliente, empresa);
    }

    public void adicionarProduto(int numero, int produto)
            throws DadosInvalidosException {
        pedidoManager.adicionarProduto(numero, produto);
    }

    public String getPedidos(int pedido, String atributo)
            throws DadosInvalidosException {
        return pedidoManager.getPedido(pedido, atributo);
    }

    public void fecharPedido(int numero)
            throws DadosInvalidosException {
        pedidoManager.fecharPedido(numero);
    }

    public void removerProduto(int pedido, String produto)
            throws DadosInvalidosException {
        pedidoManager.removerProduto(pedido, produto);
    }

    public int getNumeroPedido(String cliente, int empresa, int indice)
            throws DadosInvalidosException {
        return pedidoManager.getNumeroPedido(cliente, empresa, indice);
    }

    public void encerrarSistema() {
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha)
            throws DadosInvalidosException {
        empresaManager.alterarFuncionamento(mercado, abre, fecha);
    }

}
