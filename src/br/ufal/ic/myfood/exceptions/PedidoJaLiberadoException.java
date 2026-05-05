package br.ufal.ic.myfood.exceptions;

public class PedidoJaLiberadoException extends DadosInvalidosException {

    public PedidoJaLiberadoException() {
        super("Pedido ja liberado");
    }

}

