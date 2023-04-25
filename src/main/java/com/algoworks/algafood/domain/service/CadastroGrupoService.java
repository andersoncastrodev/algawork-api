package com.algoworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algoworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algoworks.algafood.domain.model.Grupo;
import com.algoworks.algafood.domain.model.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	public List<Grupo> consultarTodos(){
		return grupoRepository.findAll();
	}
	
	public Grupo consultarId(Long id) {
		return buscarOuFalhar(id);
	}
	
	public Grupo buscarOuFalhar(Long grupoId) {
		 return  grupoRepository.findById(grupoId).orElseThrow( ()-> new GrupoNaoEncontradoException(grupoId) );
	}
}
