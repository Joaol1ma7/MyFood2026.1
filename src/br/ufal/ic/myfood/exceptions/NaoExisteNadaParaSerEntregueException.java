package br.ufal.ic.myfood.exceptions;

public class NaoExisteNadaParaSerEntregueException extends DadosInvalidosException {

    public NaoExisteNadaParaSerEntregueException() {
        super("Nao existe nada para ser entregue com esse id");
    }

}

