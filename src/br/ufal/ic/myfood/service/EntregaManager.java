package br.ufal.ic.myfood.service;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Entrega;
import br.ufal.ic.myfood.models.Pedido;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.repositories.EntregaRepository;
import br.ufal.ic.myfood.repositories.EntregaRepositoryImpl;

import java.util.List;

public class EntregaManager {
    private EntregaRepository entregaRepository;
    private PedidoManager pedidoManager;
    private UsuarioManager usuarioManager;
    private EmpresaManager empresaManager;

    public EntregaManager(PedidoManager pedidoManager, UsuarioManager usuarioManager, EmpresaManager empresaManager) {
        this.entregaRepository = new EntregaRepositoryImpl();
        this.pedidoManager = pedidoManager;
        this.usuarioManager = usuarioManager;
        this.empresaManager = empresaManager;
    }

    public int criarEntrega(int pedidoNumero, String entregadorId, String destino) throws DadosInvalidosException {
        Pedido pedido = null;
        for (Pedido p : pedidoManager.obterTodosPedidos()) {
            if (p.getNumero() == pedidoNumero) { pedido = p; break; }
        }
        if (pedido == null) throw new PedidoNaoEncontradoException();

        if (!"pronto".equals(pedido.getEstado())) {
            throw new PedidoNaoProntoParaEntregaException();
        }

        Usuario entregador = usuarioManager.getUsuarioById(entregadorId);
        if (entregador == null || entregador.getPlaca() == null || entregador.getPlaca().isEmpty()
                || entregador.getVeiculo() == null || entregador.getVeiculo().isEmpty()) {
            throw new NaoEUmEntregadorValidoException();
        }

        for (Entrega e : entregaRepository.obterTodos()) {
            if (entregadorId.equals(e.getEntregadorId()) && "entregando".equals(e.getEstado())) {
                throw new EntregadorAindaEmEntregaException();
            }
        }

        List<String> produtos = pedidoManager.obterNomesProdutosPedido(pedidoNumero);
        String clienteNome = pedido.getNomeCliente();
        String empresaNome = pedido.getNomeEmpresa();

        String destinoFinal = destino == null || destino.isEmpty() ? usuarioManager.getUsuarioById(pedido.getCliente()).getEndereco() : destino;

        Entrega entrega = new Entrega(0, clienteNome, empresaNome, pedidoNumero, entregadorId, destinoFinal, produtos);
        entregaRepository.adicionar(entrega);

        pedidoManager.marcarPedidoComoEntregando(pedidoNumero);

        return entrega.getId();
    }

    public String getEntrega(int id, String atributo) throws DadosInvalidosException {
        if (atributo == null || atributo.isEmpty()) throw new AtributoInvalidoException();
        Entrega e = entregaRepository.obterPorId(id);
        if (e == null) throw new EntregaNaoExisteException();

        switch (atributo.toLowerCase()) {
            case "cliente":
                return e.getCliente();
            case "empresa":
                return e.getEmpresa();
            case "pedido":
                return String.valueOf(e.getPedido());
            case "entregador":
                Usuario u = usuarioManager.getUsuarioById(e.getEntregadorId());
                return u == null ? "" : u.getNome();
            case "destino":
                return e.getDestino();
            case "produtos":
                return e.getProdutos() == null ? "{[]}" : "{[" + String.join(", ", e.getProdutos()) + "]}";
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public int getIdEntregaByPedido(int pedidoNumero) throws DadosInvalidosException {
        Entrega e = entregaRepository.obterPorPedido(pedidoNumero);
        if (e == null) throw new EntregaNaoExisteException();
        return e.getId();
    }

    public void entregar(int entregaId) throws DadosInvalidosException {
        Entrega e = entregaRepository.obterPorId(entregaId);
        if (e == null) throw new NaoExisteNadaParaSerEntregueException();

        pedidoManager.marcarPedidoEntregue(e.getPedido());
        e.setEstado("entregue");
        entregaRepository.atualizar(e);
    }

    public void zerarDados() {
        entregaRepository.limpar();
    }
}


