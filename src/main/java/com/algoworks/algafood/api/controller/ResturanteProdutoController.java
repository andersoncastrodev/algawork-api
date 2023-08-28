package com.algoworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algoworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algoworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algoworks.algafood.api.model.input.ProdutoInput;
import com.algoworks.algafood.api.modelDTO.ProdutoDTO;
import com.algoworks.algafood.domain.model.Produto;
import com.algoworks.algafood.domain.model.Restaurante;
import com.algoworks.algafood.domain.repository.ProdutoRepository;
import com.algoworks.algafood.domain.service.CadastroProdutoService;
import com.algoworks.algafood.domain.service.CadastroResturanteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos")
public class ResturanteProdutoController {

	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CadastroResturanteService cadastroResturanteService;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
    private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
//	@GetMapping
//	public List<ProdutoDTO> listar(@PathVariable Long restauranteId){
//		
//		Restaurante restaurante = cadastroResturanteService.buscarOuFalhar(restauranteId);
//
//		List<Produto> todosProdutos = produtoRepository.findByRestaurante(restaurante);
//		
//		return produtoModelAssembler.toColletionModel(todosProdutos);
//	}
	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos){
		
		Restaurante restaurante = cadastroResturanteService.buscarOuFalhar(restauranteId);
		
		List<Produto> todosProdutos = null;
		
		if(incluirInativos) {
			//chamar outro consulta.
			todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
			
		}else {
			//Chamar apenas os que estão com status ativos.
			todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
		}
		
		
		return produtoModelAssembler.toColletionModel(todosProdutos);
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		return produtoModelAssembler.toModelDTO(produto);
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		
		Restaurante restaurante = cadastroResturanteService.buscarOuFalhar(restauranteId);
		
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		
		produto.setRestaurante(restaurante);
		
		produto = cadastroProdutoService.salvar(produto);
				
		return produtoModelAssembler.toModelDTO(produto);
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		
		Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		
		produtoAtual = cadastroProdutoService.salvar(produtoAtual);
		
		return produtoModelAssembler.toModelDTO(produtoAtual);
	}
	
}
