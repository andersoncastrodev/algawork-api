package com.algoworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algoworks.algafood.domain.model.Grupo;
import com.algoworks.algafood.domain.model.Usuario;
import com.algoworks.algafood.domain.model.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		//Tirando o gerenciamento Automatico do JPA.
		usuarioRepository.detach(usuario); 
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		//Verifica se o usuario tem o mesmo email.
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario) ) {
			throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
		
		return 	usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId,String senhaAtual, String novaSenha) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		if(usuario.senhaNaoCoincideCom(senhaAtual)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário");
		}
		
		usuario.setSenha(novaSenha);
	}
	
	@Transactional
	public void excluir(Long usuarioId) {
		
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
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		usuario.removerGrupo(grupo);
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
	    Usuario usuario = buscarOuFalhar(usuarioId);
	    Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
	    
	    usuario.adicionarGrupo(grupo);
	}
	
}

