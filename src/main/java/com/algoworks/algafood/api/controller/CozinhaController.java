package com.algoworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algoworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algoworks.algafood.api.modelDTO.CozinhaDTO;
import com.algoworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.model.Cozinha;
import com.algoworks.algafood.domain.model.repository.CozinhaRepository;
import com.algoworks.algafood.domain.service.CadastroCozinhaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping( value = "/cozinhas")
public class CozinhaController {

	//Obs: Usa a classe de Repository são apenas Consultas.
	//Obs: Usa a classe Service para  Cadastro, Atualizar e Deletar.
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CozinhaModelAssembler cozinhaAssembler;
	
	@GetMapping
	public List<CozinhaDTO> listar(){
		
		return cozinhaAssembler.toColletionModel(cozinhaRepository.findAll());
	}
	
	@GetMapping(value = "/{cozinhaId}")
	public Cozinha buscar(@PathVariable Long cozinhaId) {
		
		return cadastroCozinhaService.buscaOuFalhar(cozinhaId);
		
//		return cozinhaRepository.findById(cozinhaId)
//				.orElseThrow( ()-> new EntidadeNaoEncontradaException("aaaaaa") );
		
		
		
		//Codigo Antigo
//		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
//		
//		//Se usar o metodo isPresent() para verificar se o objeto existe é obrigatorio usar o get() ex: cozinha.get()
//		
//		if(cozinha.isPresent() ) {
//			return ResponseEntity.ok(cozinha.get() );
//		}
//		
//		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		
		try {
			
			return cadastroCozinhaService.salvar(cozinha);
			
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable Long cozinhaId,@Valid @RequestBody Cozinha cozinha){
		
		Cozinha cozinhaAtual = cadastroCozinhaService.buscaOuFalhar(cozinhaId);
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		try {
			
			return  cadastroCozinhaService.salvar(cozinhaAtual);
			
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		

	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT) //Caso seja sucesso o executar com metodo 
	public void remover(@PathVariable Long cozinhaId){		
	
		cadastroCozinhaService.excluir(cozinhaId);
				
	}
	
}
