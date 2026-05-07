package br.ufal.ic.myfood.exceptions;

public class NaoExistePedidoParaEntregaException extends DadosInvalidosException {

    public NaoExistePedidoParaEntregaException() {
        super("Nao existe pedido para entrega");
    }

}

