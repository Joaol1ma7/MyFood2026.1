package br.ufal.ic.myfood.exceptions;

public class EntregaNaoExisteException extends DadosInvalidosException {

    public EntregaNaoExisteException() {
        super("Nao existe entrega com esse id");
    }

}

