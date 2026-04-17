package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Pedido;
import java.util.List;

public interface PedidoRepository {
    void adicionar(Pedido pedido);
    Pedido obterPorNumero(int numero);
    List<Pedido> obterPorClienteEEmpresa(String clienteId, int empresaId);
    List<Pedido> obterTodos();
    void limpar();
    void atualizar(Pedido pedido);
}

