package com.algoworks.algafood.domain.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND) Herada da outra classe.
public class RestauranteNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradaException(String messagem) {
		super(messagem);
	}
	
	public RestauranteNaoEncontradaException(Long restauranteId) {
		this(String.format("Não existe um cadastro de Resturante com o código %d",restauranteId ));
	}
	
}
