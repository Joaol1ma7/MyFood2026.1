package br.ufal.ic.myfood.exceptions;

public class PedidoNaoEncontradoException extends DadosInvalidosException {

    public PedidoNaoEncontradoException() {
        super("Pedido nao encontrado");
    }

}

