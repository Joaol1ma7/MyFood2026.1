package br.ufal.ic.myfood.exceptions;

public class PedidoJaExisteException extends DadosInvalidosException {

    public PedidoJaExisteException() {
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
    }

}

