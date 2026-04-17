package br.ufal.ic.myfood.validators;

import br.ufal.ic.myfood.exceptions.*;

public class EmpresaValidator {

    public void validarNome(String nome) throws NomeInvalidoException {
        if (nome == null || nome.isEmpty()) {
            throw new NomeInvalidoException();
        }
    }

    public void validarAtributo(String atributo) throws AtributoInvalidoException {
        if (atributo == null || atributo.isEmpty() || atributo.trim().isEmpty()) {
            throw new AtributoInvalidoException();
        }
    }

    public void validarIndice(int indice) throws IndiceInvalidoException {
        if (indice < 0) {
            throw new IndiceInvalidoException();
        }
    }
}

