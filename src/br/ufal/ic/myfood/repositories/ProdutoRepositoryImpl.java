package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    private List<Produto> produtos;
    private int proximoId;

    public ProdutoRepositoryImpl() {
        this.produtos = new ArrayList<>();
        this.proximoId = 1;
    }

    @Override
    public void adicionar(Produto produto) {
        produto.setId(proximoId);
        produtos.add(produto);
        proximoId++;
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
        proximoId = 1;
    }

    @Override
    public void atualizar(Produto produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()) {
                produtos.set(i, produto);
                return;
            }
        }
    }
}

