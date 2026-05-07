package br.ufal.ic.myfood.exceptions;

public class NaoEUmEntregadorValidoException extends DadosInvalidosException {

    public NaoEUmEntregadorValidoException() {
        super("Nao e um entregador valido");
    }

}

