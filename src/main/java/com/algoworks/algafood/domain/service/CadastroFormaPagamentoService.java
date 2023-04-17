package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algoworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algoworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algoworks.algafood.domain.model.FormaPagamento;
import com.algoworks.algafood.domain.model.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	private static final String MSG_FORMAPAGAMENTO_EM_USO = "Forma Pagamento de código %d não pode ser removida. Pois esta em uso";
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	@Transactional
	public void excluir(Long formapagamentoId ) {
	
		try {
			
			formaPagamentoRepository.deleteById(formapagamentoId);
			formaPagamentoRepository.flush();
			
		}catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(formapagamentoId);
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_FORMAPAGAMENTO_EM_USO, formapagamentoId));
		}
		
	}
	
	public FormaPagamento buscaOuFalha(Long formapagamentoId) {
		return formaPagamentoRepository.findById(formapagamentoId)
				.orElseThrow( ()-> new FormaPagamentoNaoEncontradaException(formapagamentoId));
	}
	
	
}
