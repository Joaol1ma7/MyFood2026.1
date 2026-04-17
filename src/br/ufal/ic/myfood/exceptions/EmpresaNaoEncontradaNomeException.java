package br.ufal.ic.myfood.exceptions;

public class EmpresaNaoEncontradaNomeException extends Exception {

    public EmpresaNaoEncontradaNomeException() {
        super("Nao existe empresa com esse nome");
    }

}

