package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Produto;
import java.util.List;

public interface ProdutoRepository {
    void adicionar(Produto produto);
    Produto obterPorId(int id);
    List<Produto> obterPorEmpresa(int empresaId);
    List<Produto> obterTodos();
    void limpar();
    void atualizar(Produto produto);
}

