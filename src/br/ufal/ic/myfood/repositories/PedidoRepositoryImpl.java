package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Pedido;
import br.ufal.ic.myfood.persistence.IdCounter;
import br.ufal.ic.myfood.persistence.PersistenceManager;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepositoryImpl implements PedidoRepository {
    private static final String ARQUIVO_PEDIDOS = "pedidos";
    private static final String ARQUIVO_ID = "pedidos_id";
    private List<Pedido> pedidos;
    private IdCounter idCounter;

    public PedidoRepositoryImpl() {
        this.pedidos = PersistenceManager.carregar(ARQUIVO_PEDIDOS);
        List<IdCounter> idList = PersistenceManager.carregar(ARQUIVO_ID);
        this.idCounter = idList.isEmpty() ? new IdCounter(1) : idList.get(0);
    }

    @Override
    public void adicionar(Pedido pedido) {
        pedido.setNumero(idCounter.obterEIncrementar());
        pedidos.add(pedido);
        salvar();
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
        idCounter = new IdCounter(1);
        salvar();
    }

    @Override
    public void atualizar(Pedido pedido) {
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getNumero() == pedido.getNumero()) {
                pedidos.set(i, pedido);
                salvar();
                return;
            }
        }
    }

    private void salvar() {
        PersistenceManager.salvar(pedidos, ARQUIVO_PEDIDOS);
        List<IdCounter> idList = new ArrayList<>();
        idList.add(idCounter);
        PersistenceManager.salvar(idList, ARQUIVO_ID);
    }
}

