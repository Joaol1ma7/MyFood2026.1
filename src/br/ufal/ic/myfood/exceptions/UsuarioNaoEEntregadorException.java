package br.ufal.ic.myfood.exceptions;

public class UsuarioNaoEEntregadorException extends DadosInvalidosException {

    public UsuarioNaoEEntregadorException() {
        super("Usuario nao e um entregador");
    }

}

