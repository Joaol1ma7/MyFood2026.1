package br.ufal.ic.myfood.exceptions;

public class NaoEhMercadoValidoException extends DadosInvalidosException {

    public NaoEhMercadoValidoException() {
        super("Nao e um mercado valido");
    }

}

