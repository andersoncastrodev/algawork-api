package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algoworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algoworks.algafood.domain.model.Usuario;
import com.algoworks.algafood.domain.model.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvar(Usuario usuario) {
		
		
		return null;
	}
	
	public void remover(Long usuarioId) {
		
	}
	
	public Usuario buscarOuFalhar(Long usuarioId) {	
		
		return  usuarioRepository.findById(usuarioId)
				.orElseThrow( ()-> new UsuarioNaoEncontradoException(usuarioId) );
		
	}
}

