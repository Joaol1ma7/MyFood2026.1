package br.ufal.ic.myfood.models;

public class Produto {
    private int id;
    private String nome;
    private float valor;
    private String categoria;
    private int empresaId;

    public Produto() {
    }

    public Produto(int id, String nome, float valor, String categoria, int empresaId) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
        this.empresaId = empresaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }
}

