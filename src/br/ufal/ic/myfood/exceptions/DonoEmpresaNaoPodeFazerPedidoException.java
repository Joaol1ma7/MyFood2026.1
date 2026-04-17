package br.ufal.ic.myfood.exceptions;

public class DonoEmpresaNaoPodeFazerPedidoException extends DadosInvalidosException {

    public DonoEmpresaNaoPodeFazerPedidoException() {
        super("Dono de empresa nao pode fazer um pedido");
    }

}

