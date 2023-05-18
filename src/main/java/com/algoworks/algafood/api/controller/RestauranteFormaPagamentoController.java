package com.algoworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algoworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algoworks.algafood.api.modelDTO.FormaPagamentoDTO;
import com.algoworks.algafood.domain.model.Restaurante;
import com.algoworks.algafood.domain.service.CadastroResturanteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	@Autowired
	private CadastroResturanteService cadastroResturanteService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> listar(@PathVariable Long restauranteId ){
		
		Restaurante restaurante = cadastroResturanteService.buscaOuFalhar(restauranteId);
		
		return formaPagamentoModelAssembler.toColletionModel(restaurante.getFormasPagamento());
		
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId,@PathVariable Long formaPagamentoId) {
		cadastroResturanteService.associarFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId,@PathVariable Long formaPagamentoId) {
		cadastroResturanteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	
}
