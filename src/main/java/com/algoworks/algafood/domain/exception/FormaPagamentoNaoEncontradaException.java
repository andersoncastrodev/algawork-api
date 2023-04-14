package com.algoworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {


	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradaException(String messagem) {
		super(messagem);
	}
	
	public FormaPagamentoNaoEncontradaException(Long formapagamentoId) {
		this(String.format("Não existe um cadastro de FormaPagamento com o código %d",formapagamentoId ));
	}
	
}
