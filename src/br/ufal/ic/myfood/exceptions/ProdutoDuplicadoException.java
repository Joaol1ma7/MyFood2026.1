package br.ufal.ic.myfood.exceptions;

public class ProdutoDuplicadoException extends DadosInvalidosException {

    public ProdutoDuplicadoException() {
        super("Ja existe um produto com esse nome para essa empresa");
    }

}

