package com.algoworks.algafood.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.algoworks.algafood.api.assembler.PedidoModelAssembler;
import com.algoworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algoworks.algafood.api.modelDTO.PedidoDTO;
import com.algoworks.algafood.api.modelDTO.PedidoResumoDTO;
import com.algoworks.algafood.domain.model.Pedido;
import com.algoworks.algafood.domain.model.repository.PedidoRepository;
import com.algoworks.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@GetMapping
	public List<PedidoResumoDTO> listar(){
		List<Pedido> todosPedidos = pedidoRepository.findAll();
		return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
		return pedidoModelAssembler.toModel(pedido);
	}
	
}
