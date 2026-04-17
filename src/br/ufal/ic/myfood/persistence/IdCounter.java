package br.ufal.ic.myfood.persistence;

public class IdCounter {
    private int valor;

    public IdCounter() {
        this.valor = 1;
    }

    public IdCounter(int valor) {
        this.valor = valor;
    }

    public int obter() {
        return valor;
    }

    public int obterEIncrementar() {
        return valor++;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void incrementar() {
        valor++;
    }
}



