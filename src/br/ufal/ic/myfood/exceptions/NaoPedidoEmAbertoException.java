package br.ufal.ic.myfood.exceptions;

public class NaoPedidoEmAbertoException extends DadosInvalidosException {

    public NaoPedidoEmAbertoException() {
        super("Nao existe pedido em aberto");
    }

}

