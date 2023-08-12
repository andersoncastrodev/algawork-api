package com.algoworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algoworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algoworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algoworks.algafood.domain.model.Grupo;
import com.algoworks.algafood.domain.model.Permissao;
import com.algoworks.algafood.domain.model.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	private static final String  MSG_GRUPO_EM_USO = "Grupo de Codigo %d não pode ser removido.Pois esta em uso.%d";
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissao;
	
	public List<Grupo> consultarTodos(){
		return grupoRepository.findAll();
	}
	
	public Grupo consultarId(Long id) {
		return buscarOuFalhar(id);
	}
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		
		return grupoRepository.save(grupo);
		
	}
	
	@Transactional
	public void excluir(Long grupoId) {
		
		try {
			
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
			
		
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId); 
			
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}
	
	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);
		grupo.removerPermissoes(permissao);
	}
	
	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);
		grupo.adicionarPermissao(permissao);
	}
	
	public Grupo buscarOuFalhar(Long grupoId) {
		 return  grupoRepository.findById(grupoId).orElseThrow( ()-> new GrupoNaoEncontradoException(grupoId) );
	}
}
