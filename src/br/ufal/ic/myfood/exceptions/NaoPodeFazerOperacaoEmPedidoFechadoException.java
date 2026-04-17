package br.ufal.ic.myfood.exceptions;

public class NaoPodeFazerOperacaoEmPedidoFechadoException extends DadosInvalidosException {

    public NaoPodeFazerOperacaoEmPedidoFechadoException() {
        super("Nao e possivel remover produtos de um pedido fechado");
    }

}

