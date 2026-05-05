package br.ufal.ic.myfood.exceptions;

public class PedidoNaoProntoParaEntregaException extends DadosInvalidosException {

    public PedidoNaoProntoParaEntregaException() {
        super("Pedido nao esta pronto para entrega");
    }

}

