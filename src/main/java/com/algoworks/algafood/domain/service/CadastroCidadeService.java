package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.algoworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algoworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algoworks.algafood.domain.model.Cidade;
import com.algoworks.algafood.domain.model.Estado;
import com.algoworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida. Pois esta em uso.";

	//private static final String MSG_CIDADE_NAO_ENCONTRADO = "Não existe um cadastro de cidade com codigo %d";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		
		Long estadoId = cidade.getEstado().getId();
	
		Estado estado = cadastroEstadoService.buscaOuFalha(estadoId);
		
		cidade.setEstado( estado );
		
		return cidadeRepository.save(cidade);	
	}
	
	@Transactional
	public void excluir(Long cidadeId) {
		try {
			
			cidadeRepository.deleteById(cidadeId);
			cidadeRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
		
	}
	
	public Cidade buscarOuFalhar(Long cidadeId) {
		
		return cidadeRepository.findById(cidadeId)
			   .orElseThrow( ()-> new CidadeNaoEncontradaException(cidadeId) );
	}
}
