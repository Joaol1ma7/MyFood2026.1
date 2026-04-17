package br.ufal.ic.myfood;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.models.UsuarioManager;
import br.ufal.ic.myfood.models.EmpresaManager;
import br.ufal.ic.myfood.models.ProdutoManager;
import br.ufal.ic.myfood.models.PedidoManager;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    private static final String NO_CPF = "NO_CPF";
    private static UsuarioManager userManager;
    private static EmpresaManager empresaManager;
    private static ProdutoManager produtoManager;
    private static PedidoManager pedidoManager;

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

    public String getEmpresasDoUsuario(String idDono)
            throws UsuarioNaoPodeCriarEmpresaException {
        return empresaManager.getEmpresasDoUsuario(idDono, userManager.getUsuarios());
    }

    public String getAtributoEmpresa(int empresa, String atributo)
            throws EmpresaNaoExisteException, AtributoInvalidoException {
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

}
