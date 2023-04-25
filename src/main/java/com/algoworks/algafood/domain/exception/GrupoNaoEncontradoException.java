package com.algoworks.algafood.domain.exception;


public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public GrupoNaoEncontradoException(String messagem) {
		super(messagem);
		// TODO Auto-generated constructor stub
	}
	
	public GrupoNaoEncontradoException(Long grupoId) {
		super(String.format("Não existe um cadastro de Grupo com o código %d",grupoId ));
		// TODO Auto-generated constructor stub
	}
	

}
