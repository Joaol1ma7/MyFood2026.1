package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepositoryImpl implements PedidoRepository {
    private List<Pedido> pedidos;
    private int proximoNumero;

    public PedidoRepositoryImpl() {
        this.pedidos = new ArrayList<>();
        this.proximoNumero = 1;
    }

    @Override
    public void adicionar(Pedido pedido) {
        pedido.setNumero(proximoNumero);
        pedidos.add(pedido);
        proximoNumero++;
    }

    @Override
    public Pedido obterPorNumero(int numero) {
        for (Pedido pedido : pedidos) {
            if (pedido.getNumero() == numero) {
                return pedido;
            }
        }
        return null;
    }

    @Override
    public List<Pedido> obterPorClienteEEmpresa(String clienteId, int empresaId) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().equals(clienteId) && pedido.getEmpresa().equals(String.valueOf(empresaId))) {
                resultado.add(pedido);
            }
        }
        return resultado;
    }

    @Override
    public List<Pedido> obterTodos() {
        return new ArrayList<>(pedidos);
    }

    @Override
    public void limpar() {
        pedidos.clear();
        proximoNumero = 1;
    }

    @Override
    public void atualizar(Pedido pedido) {
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getNumero() == pedido.getNumero()) {
                pedidos.set(i, pedido);
                return;
            }
        }
    }
}

