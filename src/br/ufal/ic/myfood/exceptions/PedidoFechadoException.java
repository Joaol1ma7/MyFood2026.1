package br.ufal.ic.myfood.exceptions;

public class PedidoFechadoException extends DadosInvalidosException {

    public PedidoFechadoException() {
        super("Nao e possivel adcionar produtos a um pedido fechado");
    }

}

