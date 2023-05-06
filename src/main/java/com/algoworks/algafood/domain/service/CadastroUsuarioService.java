package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algoworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algoworks.algafood.domain.model.Usuario;
import com.algoworks.algafood.domain.model.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		return 	usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void remover(Long usuarioId) {
		
		try {
			usuarioRepository.deleteById(usuarioId);
			usuarioRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			
			throw new UsuarioNaoEncontradoException(usuarioId);
		}
	}
	
	public Usuario buscarOuFalhar(Long usuarioId) {	
		
		return  usuarioRepository.findById(usuarioId)
				.orElseThrow( ()-> new UsuarioNaoEncontradoException(usuarioId) );
		
	}
}

