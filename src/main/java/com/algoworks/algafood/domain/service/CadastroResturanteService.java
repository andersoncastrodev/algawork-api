package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algoworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algoworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algoworks.algafood.domain.model.Cozinha;
import com.algoworks.algafood.domain.model.Restaurante;
import com.algoworks.algafood.domain.model.repository.RestauranteRepository;

@Service
public class CadastroResturanteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de Codigo %d não pode ser removido.Pois esta em uso.%d";

	//private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não Existe um cadastro de Restaurante com o codigo %d";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cadastroCozinhaService.buscaOuFalhar(cozinhaId);
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void remover(Long restauranteId) {
		
		try {
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush();
		}
		catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradaException(restauranteId);
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
		
	}

	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscaOuFalhar(restauranteId);
		
		//restauranteAtual.setAtivo(true);
		  restauranteAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscaOuFalhar(restauranteId);
		
		//restauranteAtual.setAtivo(false);
		  restauranteAtual.inativar();
	}
	
	public Restaurante buscaOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow( ()-> new RestauranteNaoEncontradaException(restauranteId) );
	}
	
	

}
