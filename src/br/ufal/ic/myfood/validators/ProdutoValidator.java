package br.ufal.ic.myfood.validators;

import br.ufal.ic.myfood.exceptions.*;

public class ProdutoValidator {

    public void validarNome(String nome) throws NomeInvalidoException {
        if (nome == null || nome.isEmpty()) {
            throw new NomeInvalidoException();
        }
    }

    public void validarValor(float valor) throws ValorInvalidoException {
        if (valor < 0) {
            throw new ValorInvalidoException();
        }
    }

    public void validarCategoria(String categoria) throws CategoriaInvalidaException {
        if (categoria == null || categoria.isEmpty()) {
            throw new CategoriaInvalidaException();
        }
    }

    public void validarAtributo(String atributo) throws AtributoNaoExisteException {
        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoNaoExisteException();
        }

        switch(atributo.toLowerCase()) {
            case "id":
            case "nome":
            case "valor":
            case "categoria":
            case "empresa":
                return;
            default:
                throw new AtributoNaoExisteException();
        }
    }
}

