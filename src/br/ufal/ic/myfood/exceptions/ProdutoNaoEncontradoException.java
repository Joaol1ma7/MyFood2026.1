package br.ufal.ic.myfood.exceptions;

public class ProdutoNaoEncontradoException extends DadosInvalidosException {

    public ProdutoNaoEncontradoException() {
        super("Produto nao encontrado");
    }

}



