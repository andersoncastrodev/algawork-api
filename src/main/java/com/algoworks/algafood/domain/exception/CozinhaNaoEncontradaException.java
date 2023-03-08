package com.algoworks.algafood.domain.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND) Herada da outra classe.
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String messagem) {
		super(messagem);
	}
	
	public CozinhaNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe um cadastro de Cozinha com o código %d",cozinhaId ));
	}
	
}
