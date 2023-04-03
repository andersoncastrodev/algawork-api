package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.algoworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algoworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algoworks.algafood.domain.model.Estado;
import com.algoworks.algafood.domain.model.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida. Pois esta em uso";
	
	//private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de Estado com o código %d";
	
	@Autowired
	private EstadoRepository estadoRepository;
	      
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public void excluir(Long estadoId) {
		
		try {
			
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();
			
		}catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradaException(estadoId);
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
		}
		
		
	}
	
	public Estado buscaOuFalha(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow( ()-> new EstadoNaoEncontradaException(estadoId));
	}
	
}
