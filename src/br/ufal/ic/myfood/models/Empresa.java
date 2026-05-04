package br.ufal.ic.myfood.models;

public class Empresa {
    private int id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String tipoMercado;
    private String abre;
    private String fecha;
    private Boolean aberto24Horas;
    private int numeroFuncionarios;
    private String tipo;
    private String idDono;
    private String nomeDono;

    public Empresa() {
    }

    public Empresa(int id, String nome, String endereco, String tipoCozinha, String idDono, String nomeDono) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.tipo = "restaurante";
        this.idDono = idDono;
        this.nomeDono = nomeDono;
    }

    public Empresa(int id, String nome, String endereco, String tipo, String abre, String fecha, String tipoMercado, String idDono, String nomeDono) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
        this.idDono = idDono;
        this.nomeDono = nomeDono;
    }

    public Empresa(int id, String nome, String endereco, String tipo, Boolean aberto24Horas, int numeroFuncionarios, String idDono, String nomeDono) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.aberto24Horas = aberto24Horas;
        this.numeroFuncionarios = numeroFuncionarios;
        this.idDono = idDono;
        this.nomeDono = nomeDono;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    public String getIdDono() {
        return idDono;
    }

    public void setIdDono(String idDono) {
        this.idDono = idDono;
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public String getTipoMercado() {
        return tipoMercado;
    }

    public void setTipoMercado(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }

    public String getAbre() {
        return abre;
    }

    public void setAbre(String abre) {
        this.abre = abre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getAberto24Horas() {
        return aberto24Horas;
    }

    public void setAberto24Horas(Boolean aberto24Horas) {
        this.aberto24Horas = aberto24Horas;
    }

    public int getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(int numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }
}


