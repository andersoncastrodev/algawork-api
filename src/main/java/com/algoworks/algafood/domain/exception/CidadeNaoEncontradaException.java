package com.algoworks.algafood.domain.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND) Herada da outra classe.
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String messagem) {
		super(messagem);
	}
	
	public CidadeNaoEncontradaException(Long cidadeId) {
		this(String.format("Não existe um cadastro de Cidade com o código %d",cidadeId ));
	}
	
}
