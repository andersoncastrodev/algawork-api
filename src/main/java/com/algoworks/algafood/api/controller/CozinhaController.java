package com.algoworks.algafood.api.controller;

import java.util.List;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import com.algoworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algoworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algoworks.algafood.api.model.input.CozinhaInput;
import com.algoworks.algafood.api.modelDTO.CozinhaDTO;
import com.algoworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.model.Cozinha;
import com.algoworks.algafood.domain.repository.CozinhaRepository;
import com.algoworks.algafood.domain.service.CadastroCozinhaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	@Autowired
	private CozinhaInputDisassembler cozinhatDisassembler;
	
	@GetMapping
	public Page<CozinhaDTO> listar(Pageable pageable){
		
		log.info("Consultando cozinhas com páginas de {} registros...", pageable.getPageSize());
		
		Page<Cozinha> todasCozinhas = cozinhaRepository.findAll(pageable);
		
		List<CozinhaDTO> cozinhaDTOs = cozinhaAssembler.toColletionModel(todasCozinhas.getContent());
		
		Page<CozinhaDTO> cozinhaDTOsPage = new PageImpl<>(cozinhaDTOs, pageable, todasCozinhas.getTotalElements());
		
		return cozinhaDTOsPage;
	}
	
	@GetMapping(value = "/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		
		return cozinhaAssembler.toModelDTO(cadastroCozinhaService.buscaOuFalhar(cozinhaId));
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		try {
			Cozinha cozinha = cozinhatDisassembler.toDomainObject(cozinhaInput);
			
			return cozinhaAssembler.toModelDTO( cadastroCozinhaService.salvar(cozinha) );
			
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId,@Valid @RequestBody CozinhaInput cozinhaInput){
		
		
		Cozinha cozinhaAtual = cadastroCozinhaService.buscaOuFalhar(cozinhaId);
		
		cozinhatDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		
		//BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		try {
			
			return cozinhaAssembler.toModelDTO( cadastroCozinhaService.salvar(cozinhaAtual) );
			
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
