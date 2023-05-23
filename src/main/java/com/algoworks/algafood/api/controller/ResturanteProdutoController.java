package com.algoworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.algoworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algoworks.algafood.api.modelDTO.ProdutoDTO;
import com.algoworks.algafood.domain.model.Restaurante;
import com.algoworks.algafood.domain.service.CadastroProdutoService;
import com.algoworks.algafood.domain.service.CadastroResturanteService;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos")
public class ResturanteProdutoController {

	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CadastroResturanteService cadastroResturanteService;
	
	@Autowired
    private ProdutoModelAssembler produtoModelAssembler;
	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId){
		
		Restaurante restaurante = cadastroResturanteService.buscaOuFalhar(restauranteId);

		return produtoModelAssembler.toColletionModel(restaurante.getProdutos());
	}
}
