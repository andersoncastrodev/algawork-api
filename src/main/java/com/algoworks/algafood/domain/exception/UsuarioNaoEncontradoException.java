package com.algoworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;
	
	public UsuarioNaoEncontradoException(String messagem) {
		super(messagem);	
	}
	
	public UsuarioNaoEncontradoException(Long usuarioId) {
		this(String.format("Não existe um cadastro de Resturante com o código %d",usuarioId ));
	}


}
