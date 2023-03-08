package com.algoworks.algafood.domain.exception;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {

	//Classe para erros mais genericos.
	private static final long serialVersionUID = 1L;

	public NegocioException(String messagem) {
		super(messagem);
	}
	
	//Adicionando um Construtor que mostra a causa.
	public NegocioException(String messagem, Throwable causa) {
		super(messagem, causa);
	}
}
