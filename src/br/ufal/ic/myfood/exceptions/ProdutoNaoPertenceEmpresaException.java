package br.ufal.ic.myfood.exceptions;

public class ProdutoNaoPertenceEmpresaException extends DadosInvalidosException {

    public ProdutoNaoPertenceEmpresaException() {
        super("O produto nao pertence a essa empresa");
    }

}

