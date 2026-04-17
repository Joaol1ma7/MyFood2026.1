package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.persistence.IdCounter;
import br.ufal.ic.myfood.persistence.PersistenceManager;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    private static final String ARQUIVO_PRODUTOS = "produtos";
    private static final String ARQUIVO_ID = "produtos_id";
    private List<Produto> produtos;
    private IdCounter idCounter;

    public ProdutoRepositoryImpl() {
        this.produtos = PersistenceManager.carregar(ARQUIVO_PRODUTOS);
        List<IdCounter> idList = PersistenceManager.carregar(ARQUIVO_ID);
        this.idCounter = idList.isEmpty() ? new IdCounter(1) : idList.get(0);
    }

    @Override
    public void adicionar(Produto produto) {
        produto.setId(idCounter.obterEIncrementar());
        produtos.add(produto);
        salvar();
    }

    @Override
    public Produto obterPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    @Override
    public List<Produto> obterPorEmpresa(int empresaId) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getEmpresaId() == empresaId) {
                resultado.add(produto);
            }
        }
        return resultado;
    }

    @Override
    public List<Produto> obterTodos() {
        return new ArrayList<>(produtos);
    }

    @Override
    public void limpar() {
        produtos.clear();
        idCounter = new IdCounter(1);
        salvar();
    }

    @Override
    public void atualizar(Produto produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()) {
                produtos.set(i, produto);
                salvar();
                return;
            }
        }
    }

    private void salvar() {
        PersistenceManager.salvar(produtos, ARQUIVO_PRODUTOS);
        List<IdCounter> idList = new ArrayList<>();
        idList.add(idCounter);
        PersistenceManager.salvar(idList, ARQUIVO_ID);
    }
}

