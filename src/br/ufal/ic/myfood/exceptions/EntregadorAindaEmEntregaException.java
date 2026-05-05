package br.ufal.ic.myfood.exceptions;

public class EntregadorAindaEmEntregaException extends DadosInvalidosException {

    public EntregadorAindaEmEntregaException() {
        super("Entregador ainda em entrega");
    }

}

