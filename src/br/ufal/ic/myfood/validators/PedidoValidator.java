package br.ufal.ic.myfood.validators;

import br.ufal.ic.myfood.exceptions.*;

public class PedidoValidator {

    public void validarAtributo(String atributo) throws AtributoInvalidoException, AtributoNaoExisteException {
        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        switch(atributo.toLowerCase()) {
            case "cliente":
            case "empresa":
            case "estado":
            case "produtos":
            case "valor":
                return;
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public void validarProduto(String nomeProduto) throws ProdutoInvalidoException {
        if (nomeProduto == null || nomeProduto.isEmpty()) {
            throw new ProdutoInvalidoException();
        }
    }
}

