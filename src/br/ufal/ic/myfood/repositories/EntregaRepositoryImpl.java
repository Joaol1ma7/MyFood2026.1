package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Entrega;
import br.ufal.ic.myfood.persistence.IdCounter;
import br.ufal.ic.myfood.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.List;

public class EntregaRepositoryImpl implements EntregaRepository {
    private static final String ARQUIVO_ENTREGAS = "entregas";
    private static final String ARQUIVO_ID = "entregas_id";
    private List<Entrega> entregas;
    private IdCounter idCounter;

    public EntregaRepositoryImpl() {
        this.entregas = PersistenceManager.carregar(ARQUIVO_ENTREGAS);
        List<IdCounter> idList = PersistenceManager.carregar(ARQUIVO_ID);
        this.idCounter = idList.isEmpty() ? new IdCounter(1) : idList.get(0);
    }

    @Override
    public void adicionar(Entrega entrega) {
        entrega.setId(idCounter.obterEIncrementar());
        entregas.add(entrega);
        salvar();
    }

    @Override
    public Entrega obterPorId(int id) {
        for (Entrega e : entregas) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    @Override
    public Entrega obterPorPedido(int pedido) {
        for (Entrega e : entregas) {
            if (e.getPedido() == pedido) return e;
        }
        return null;
    }

    @Override
    public List<Entrega> obterTodos() {
        return new ArrayList<>(entregas);
    }

    @Override
    public void atualizar(Entrega entrega) {
        for (int i = 0; i < entregas.size(); i++) {
            if (entregas.get(i).getId() == entrega.getId()) {
                entregas.set(i, entrega);
                salvar();
                return;
            }
        }
    }

    @Override
    public void limpar() {
        entregas.clear();
        idCounter = new IdCounter(1);
        salvar();
    }

    private void salvar() {
        PersistenceManager.salvar(entregas, ARQUIVO_ENTREGAS);
        List<IdCounter> idList = new ArrayList<>();
        idList.add(idCounter);
        PersistenceManager.salvar(idList, ARQUIVO_ID);
    }
}

