package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.repositories.PedidoRepository;
import br.ufal.ic.myfood.repositories.PedidoRepositoryImpl;
import br.ufal.ic.myfood.validators.PedidoValidator;

import java.util.List;
import java.util.Locale;

public class PedidoManager {
    private PedidoRepository pedidoRepository;
    private PedidoValidator pedidoValidator;
    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;
    private EmpresaRepository empresaRepository;

    public interface ProdutoRepository {
        Produto obterPorId(int id);
        Produto obterPorIdEEmpresa(int id, int empresaId);
    }

    public interface UsuarioRepository {
        Usuario obterPorId(String id);
    }

    public interface EmpresaRepository {
        Empresa obterPorId(int id);
        Usuario getDonoEmpresa(int id);
    }

    public PedidoManager() {
        this.pedidoRepository = new PedidoRepositoryImpl();
        this.pedidoValidator = new PedidoValidator();
        this.produtoRepository = null;
        this.usuarioRepository = null;
        this.empresaRepository = null;
    }

    public void setProdutoRepository(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void setEmpresaRepository(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public int criarPedido(String clienteId, int empresaId)
            throws DadosInvalidosException {

        if (usuarioRepository != null) {
            Usuario usuario = usuarioRepository.obterPorId(clienteId);
            if (usuario != null && empresaRepository != null) {
                Empresa empresa = empresaRepository.obterPorId(empresaId);
                if (empresa != null && empresa.getIdDono().equals(clienteId)) {
                    throw new DonoEmpresaNaoPodeFazerPedidoException();
                }
            }
        }

        List<Pedido> pedidosAbertos = pedidoRepository.obterPorClienteEEmpresa(clienteId, empresaId);
        for (Pedido pedido : pedidosAbertos) {
            if ("aberto".equals(pedido.getEstado())) {
                throw new PedidoJaExisteException();
            }
        }

        String nomeCliente = "";
        if (usuarioRepository != null) {
            Usuario usuario = usuarioRepository.obterPorId(clienteId);
            if (usuario != null) {
                nomeCliente = usuario.getNome();
            }
        }

        String nomeEmpresa = "";
        if (empresaRepository != null) {
            Empresa empresa = empresaRepository.obterPorId(empresaId);
            if (empresa != null) {
                nomeEmpresa = empresa.getNome();
            }
        }

        Pedido pedido = new Pedido(0, clienteId, nomeCliente, String.valueOf(empresaId), nomeEmpresa);
        pedidoRepository.adicionar(pedido);

        return pedido.getNumero();
    }

    public void adicionarProduto(int numeroPedido, int produtoId)
            throws DadosInvalidosException {

        Pedido pedido = pedidoRepository.obterPorNumero(numeroPedido);
        if (pedido == null) {
            throw new NaoPedidoEmAbertoException();
        }

        if (!"aberto".equals(pedido.getEstado())) {
            throw new PedidoFechadoException();
        }

        if (produtoRepository != null) {
            int empresaId = Integer.parseInt(pedido.getEmpresa());
            Produto produto = produtoRepository.obterPorIdEEmpresa(produtoId, empresaId);
            if (produto == null) {
                throw new ProdutoNaoPertenceEmpresaException();
            }
        }

        pedido.adicionarProduto(produtoId);
        pedidoRepository.atualizar(pedido);
    }

    public String getPedido(int numeroPedido, String atributo)
            throws DadosInvalidosException {

        pedidoValidator.validarAtributo(atributo);

        Pedido pedido = pedidoRepository.obterPorNumero(numeroPedido);
        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        switch(atributo.toLowerCase()) {
            case "cliente":
                return pedido.getNomeCliente();
            case "empresa":
                return pedido.getNomeEmpresa();
            case "estado":
                return pedido.getEstado();
            case "produtos":
                return formatarProdutos(pedido);
            case "valor":
                return formatarValor(calcularValorPedido(pedido));
            default:
                throw new AtributoNaoExisteException();
        }
    }

    private String formatarProdutos(Pedido pedido) {
        StringBuilder resultado = new StringBuilder();
        resultado.append("{[");

        boolean primeiro = true;
        for (int produtoId : pedido.getProdutosIds()) {
            if (!primeiro) resultado.append(", ");
            if (produtoRepository != null) {
                Produto produto = produtoRepository.obterPorId(produtoId);
                if (produto != null) {
                    resultado.append(produto.getNome());
                }
            }
            primeiro = false;
        }

        resultado.append("]}");
        return resultado.toString();
    }

    private float calcularValorPedido(Pedido pedido) {
        float valor = 0;
        for (int produtoId : pedido.getProdutosIds()) {
            if (produtoRepository != null) {
                Produto produto = produtoRepository.obterPorId(produtoId);
                if (produto != null) {
                    valor += produto.getValor();
                }
            }
        }
        return valor;
    }

    private String formatarValor(float valor) {
        return String.format(Locale.US, "%.2f", valor);
    }

    public void fecharPedido(int numeroPedido)
            throws DadosInvalidosException {

        Pedido pedido = pedidoRepository.obterPorNumero(numeroPedido);
        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        pedido.setEstado("preparando");
        pedidoRepository.atualizar(pedido);
    }

    public void removerProduto(int numeroPedido, String nomeProduto)
            throws DadosInvalidosException {

        pedidoValidator.validarProduto(nomeProduto);

        Pedido pedido = pedidoRepository.obterPorNumero(numeroPedido);
        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        if (!"aberto".equals(pedido.getEstado())) {
            throw new NaoPodeFazerOperacaoEmPedidoFechadoException();
        }

        int produtoIdParaRemover = -1;
        for (int produtoId : pedido.getProdutosIds()) {
            if (produtoRepository != null) {
                Produto produto = produtoRepository.obterPorId(produtoId);
                if (produto != null && produto.getNome().equals(nomeProduto)) {
                    produtoIdParaRemover = produtoId;
                    break;
                }
            }
        }

        if (produtoIdParaRemover == -1) {
            throw new ProdutoNaoEncontradoEmPedidoException();
        }

        pedido.removerProduto(produtoIdParaRemover);
        pedidoRepository.atualizar(pedido);
    }

    public int getNumeroPedido(String clienteId, int empresaId, int indice)
            throws DadosInvalidosException {

        List<Pedido> pedidos = pedidoRepository.obterPorClienteEEmpresa(clienteId, empresaId);

        if (indice >= pedidos.size()) {
            throw new PedidoNaoEncontradoException();
        }

        return pedidos.get(indice).getNumero();
    }

    public void zerarDados() {
        pedidoRepository.limpar();
    }
}

