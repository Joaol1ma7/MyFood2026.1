package br.ufal.ic.myfood.exceptions;

public class ProdutoNaoCadastradoException extends DadosInvalidosException {

    public ProdutoNaoCadastradoException() {
        super("Produto nao cadastrado");
    }

}


