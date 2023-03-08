package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.algoworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algoworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algoworks.algafood.domain.model.Cidade;
import com.algoworks.algafood.domain.model.Estado;
import com.algoworks.algafood.domain.model.repository.CidadeRepository;

@Component
public class CadastroCidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida. Pois esta em uso.";

	//private static final String MSG_CIDADE_NAO_ENCONTRADO = "Não existe um cadastro de cidade com codigo %d";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	public Cidade salvar(Cidade cidade) {
		
		Long estadoId = cidade.getEstado().getId();
	
		Estado estado = cadastroEstadoService.buscaOuFalha(estadoId);
		
		//Menos codigo e Mais utilizado		
//		Estado estado = estadoRepository.findById(estadoId) 
//				 .orElseThrow( ()-> new EntidadeNaoEncontradaException
//						 (String.format("Não existe cadastro de estado com o código %d", estadoId)) );

		// Metodo mais Antigo	
//		Optional<Estado> estado = estadoRepository.findById(estadoId); 
//		if( estado.isEmpty() ) {
//			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com o código %d", estadoId));
//		}		
//		cidade.setEstado( estado.get() );
		
		cidade.setEstado( estado );
		
		return cidadeRepository.save(cidade);	
	}
	
	public void excluir(Long cidadeId) {
		try {
			
			cidadeRepository.deleteById(cidadeId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
		
	}
	
	public Cidade buscaOuFalhar(Long cidadeId) {
		
		return cidadeRepository.findById(cidadeId)
			   .orElseThrow( ()-> new CidadeNaoEncontradaException(cidadeId) );
	}
}
