package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algoworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algoworks.algafood.domain.model.Permissao;
import com.algoworks.algafood.domain.model.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;

	public Permissao buscarOuFalhar(Long permissaoId) {
	   return permissaoRepository.findById(permissaoId)
	     .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	}		
}
