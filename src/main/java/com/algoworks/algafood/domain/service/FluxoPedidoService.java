package com.algoworks.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.model.Pedido;
import com.algoworks.algafood.domain.model.StatusPedido;

import jakarta.transaction.Transactional;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
		pedido.confirmar();
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
	    Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
	    pedido.cancelar();
	}

	@Transactional
	public void entregar(Long pedidoId) {
	    Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
	    pedido.entregar();
	}
}
