package br.ufal.ic.myfood.exceptions;

public class EmpresaDuplicadaException extends Exception {

    public EmpresaDuplicadaException() {
        super("Proibido cadastrar duas empresas com o mesmo nome e local");
    }

}

