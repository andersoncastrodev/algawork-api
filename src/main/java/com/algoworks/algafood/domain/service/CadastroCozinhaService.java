package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algoworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algoworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algoworks.algafood.domain.model.Cozinha;
import com.algoworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de Codigo %d não pode ser removida.Pois esta em uso.";

	//private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não Existe um cadastro de cozinha com o codigo %d";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@Transactional
	public void excluir(Long cozinhaId) {
		
		try {
			
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush(); //Diz para JPA descarregar tudo que ia salva no banco de dados.
		}
		catch (EmptyResultDataAccessException e) {
			
			throw new CozinhaNaoEncontradaException(cozinhaId);
		}
		catch (DataIntegrityViolationException e) {
			
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
		
	}
	
	//Para Evitar que essse codigo fique se repetindo nos controllers.
	public Cozinha buscaOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow( ()-> new CozinhaNaoEncontradaException(cozinhaId) );
	}
	
	
}
