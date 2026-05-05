package br.ufal.ic.myfood.exceptions;

public class NaoEPosivelLiberarPedidoException extends DadosInvalidosException {

    public NaoEPosivelLiberarPedidoException() {
        super("Nao e possivel liberar um produto que nao esta sendo preparado");
    }

}

