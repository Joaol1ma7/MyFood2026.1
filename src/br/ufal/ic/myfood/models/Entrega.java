package br.ufal.ic.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class Entrega {
    private int id;
    private String cliente;
    private String empresa;
    private int pedido;
    private String entregadorId;
    private String destino;
    private List<String> produtos;
    private String estado;

    public Entrega() {
        this.produtos = new ArrayList<>();
    }

    public Entrega(int id, String cliente, String empresa, int pedido, String entregadorId, String destino, List<String> produtos) {
        this.id = id;
        this.cliente = cliente;
        this.empresa = empresa;
        this.pedido = pedido;
        this.entregadorId = entregadorId;
        this.destino = destino;
        this.produtos = produtos == null ? new ArrayList<>() : produtos;
        this.estado = "entregando";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public int getPedido() { return pedido; }
    public void setPedido(int pedido) { this.pedido = pedido; }

    public String getEntregadorId() { return entregadorId; }
    public void setEntregadorId(String entregadorId) { this.entregadorId = entregadorId; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public List<String> getProdutos() { return produtos; }
    public void setProdutos(List<String> produtos) { this.produtos = produtos; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

