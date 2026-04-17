package br.ufal.ic.myfood.exceptions;

public class ProdutoNaoEncontradoEmPedidoException extends DadosInvalidosException {

    public ProdutoNaoEncontradoEmPedidoException() {
        super("Produto nao encontrado");
    }

}

