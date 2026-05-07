package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Entrega;
import java.util.List;

public interface EntregaRepository {
    void adicionar(Entrega entrega);
    Entrega obterPorId(int id);
    Entrega obterPorPedido(int pedido);
    List<Entrega> obterTodos();
    void atualizar(Entrega entrega);
    void limpar();
}

