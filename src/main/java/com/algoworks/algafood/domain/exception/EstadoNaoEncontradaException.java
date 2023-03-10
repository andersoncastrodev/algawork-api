package com.algoworks.algafood.domain.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND) Herada da outra classe.
public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradaException(String messagem) {
		super(messagem);
	}
	
	public EstadoNaoEncontradaException(Long estadoId) {
		this(String.format("Não existe um cadastro de Estado com o código %d",estadoId ));
	}
	
}
