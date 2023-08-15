package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algoworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algoworks.algafood.domain.model.Pedido;
import com.algoworks.algafood.domain.model.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId)
				.orElseThrow( ()-> new PedidoNaoEncontradoException(pedidoId) );
	}
}
