package br.ufal.ic.myfood.service;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.repositories.ProdutoRepository;
import br.ufal.ic.myfood.repositories.ProdutoRepositoryImpl;
import br.ufal.ic.myfood.validators.ProdutoValidator;

import java.util.List;
import java.util.Locale;

public class ProdutoManager {
    private ProdutoRepository produtoRepository;
    private ProdutoValidator produtoValidator;
    private EmpresaRepository empresaRepository;

    public interface EmpresaRepository {
        Empresa obterPorId(int id);
    }

    public ProdutoManager() {
        this(null);
    }

    public ProdutoManager(EmpresaRepository empresaRepository) {
        this.produtoRepository = new ProdutoRepositoryImpl();
        this.produtoValidator = new ProdutoValidator();
        this.empresaRepository = empresaRepository;
    }

    public void setEmpresaRepository(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Produto obterProdutoPorId(int id) {
        return produtoRepository.obterPorId(id);
    }

    public Produto obterProdutoPorIdEEmpresa(int id, int empresaId) {
        Produto produto = produtoRepository.obterPorId(id);
        if (produto != null && produto.getEmpresaId() == empresaId) {
            return produto;
        }
        return null;
    }

    public int criarProduto(int empresaId, String nome, float valor, String categoria)
            throws DadosInvalidosException, EmpresaNaoExisteException {

        produtoValidator.validarNome(nome);
        produtoValidator.validarValor(valor);
        produtoValidator.validarCategoria(categoria);

        for (Produto produto : produtoRepository.obterPorEmpresa(empresaId)) {
            if (produto.getNome().equals(nome)) {
                throw new ProdutoDuplicadoException();
            }
        }

        Produto produto = new Produto(0, nome, valor, categoria, empresaId);
        produtoRepository.adicionar(produto);

        return produto.getId();
    }

    public void editarProduto(int produtoId, String nome, float valor, String categoria)
            throws DadosInvalidosException {

        Produto produto = produtoRepository.obterPorId(produtoId);
        if (produto == null) {
            throw new ProdutoNaoCadastradoException();
        }

        produtoValidator.validarNome(nome);
        produtoValidator.validarValor(valor);
        produtoValidator.validarCategoria(categoria);

        for (Produto p : produtoRepository.obterPorEmpresa(produto.getEmpresaId())) {
            if (p.getNome().equals(nome) && p.getId() != produtoId) {
                throw new ProdutoDuplicadoException();
            }
        }

        produto.setNome(nome);
        produto.setValor(valor);
        produto.setCategoria(categoria);
        produtoRepository.atualizar(produto);
    }

    public String getProduto(String nomeProduto, int empresaId, String atributo)
            throws DadosInvalidosException {

        produtoValidator.validarAtributo(atributo);

        Produto produto = null;
        for (Produto p : produtoRepository.obterPorEmpresa(empresaId)) {
            if (p.getNome().equals(nomeProduto)) {
                produto = p;
                break;
            }
        }

        if (produto == null) {
            throw new ProdutoNaoEncontradoException();
        }

        switch(atributo.toLowerCase()) {
            case "id":
                return String.valueOf(produto.getId());
            case "nome":
                return produto.getNome();
            case "valor":
                return String.format(Locale.US, "%.2f", produto.getValor());
            case "categoria":
                return produto.getCategoria();
            case "empresa":
                if (empresaRepository != null) {
                    Empresa empresa = empresaRepository.obterPorId(empresaId);
                    if (empresa != null) {
                        return empresa.getNome();
                    }
                }
                return "";
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public String listarProdutos(int empresaId)
            throws EmpresaNaoExisteException {

        if (empresaRepository != null) {
            Empresa empresa = empresaRepository.obterPorId(empresaId);
            if (empresa == null) {
                throw new EmpresaNaoExisteException();
            }
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("{[");

        List<Produto> produtos = produtoRepository.obterPorEmpresa(empresaId);
        boolean primeiro = true;
        for (Produto produto : produtos) {
            if (!primeiro) resultado.append(", ");
            resultado.append(produto.getNome());
            primeiro = false;
        }

        resultado.append("]}");

        return resultado.toString();
    }

    public void zerarDados() {
        produtoRepository.limpar();
    }
}








