package br.ufal.ic.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int numero;
    private String cliente;
    private String nomeCliente;
    private String empresa;
    private String nomeEmpresa;
    private String estado;
    private List<Integer> produtosIds;

    public Pedido() {
        this.produtosIds = new ArrayList<>();
    }

    public Pedido(int numero, String cliente, String nomeCliente, String empresa, String nomeEmpresa) {
        this.numero = numero;
        this.cliente = cliente;
        this.nomeCliente = nomeCliente;
        this.empresa = empresa;
        this.nomeEmpresa = nomeEmpresa;
        this.estado = "aberto";
        this.produtosIds = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Integer> getProdutosIds() {
        return produtosIds;
    }

    public void setProdutosIds(List<Integer> produtosIds) {
        this.produtosIds = produtosIds;
    }

    public void adicionarProduto(int produtoId) {
        produtosIds.add(produtoId);
    }

    public void removerProduto(int produtoId) {
        produtosIds.remove(Integer.valueOf(produtoId));
    }
}

