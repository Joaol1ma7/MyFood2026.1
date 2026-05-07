package br.ufal.ic.myfood.exceptions;

public class EntregadorSemEmpresaException extends DadosInvalidosException {

    public EntregadorSemEmpresaException() {
        super("Entregador nao estar em nenhuma empresa.");
    }

}

