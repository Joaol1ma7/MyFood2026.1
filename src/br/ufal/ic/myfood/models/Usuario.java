package br.ufal.ic.myfood.models;

import java.util.UUID;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;
    private String cpf;
    private String veiculo;
    private String placa;

    public Usuario() {
        this.id = UUID.randomUUID().toString();
    }

    public Usuario(String nome, String email, String senha, String endereco) {
        this(nome, email, senha, endereco, null);
    }

    public Usuario(String nome, String email, String senha, String endereco, String cpf) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.cpf = cpf;
        this.id = UUID.randomUUID().toString();
    }

    public Usuario(String nome, String email, String senha, String endereco, String cpf, String veiculo, String placa) {
        this(nome, email, senha, endereco, cpf);
        this.veiculo = veiculo;
        this.placa = placa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
