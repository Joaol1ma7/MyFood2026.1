package br.ufal.ic.myfood.exceptions;

public class AtributoNaoExisteException extends DadosInvalidosException {

    public AtributoNaoExisteException() {
        super("Atributo nao existe");
    }

}

