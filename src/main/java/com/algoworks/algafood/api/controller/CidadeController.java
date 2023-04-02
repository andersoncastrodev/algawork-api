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

import com.algoworks.algafood.api.assembler.CidadeInputDiassembler;
import com.algoworks.algafood.api.assembler.CidadeModelAssembler;
import com.algoworks.algafood.api.model.input.CidadeInput;
import com.algoworks.algafood.api.modelDTO.CidadeDTO;
import com.algoworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algoworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.model.Cidade;
import com.algoworks.algafood.domain.model.repository.CidadeRepository;
import com.algoworks.algafood.domain.service.CadastroCidadeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDiassembler cidadeInputDiassembler;
	
	@GetMapping
	public List<CidadeDTO> listar(){
		
		return cidadeModelAssembler.toColletionModel( cidadeRepository.findAll() );
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId ) {
		
		return cidadeModelAssembler.toModelDTO( cadastroCidadeService.buscaOuFalhar(cidadeId)) ;	
	}
	

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput){
		
		try {
			
			Cidade cidade = cidadeInputDiassembler.toDomainObject(cidadeInput);
			
			return cidadeModelAssembler.toModelDTO( cadastroCidadeService.salvar(cidade ) );
			
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}			
	}
	
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidade){

		try {
			
			Cidade cidadeAtual = cadastroCidadeService.buscaOuFalhar(cidadeId);
			
			cidadeInputDiassembler.copyToDomainObject(cidade, cidadeAtual);
			//BeanUtils.copyProperties(cidade, cidadeAtual, "id");			
			
			return cidadeModelAssembler.toModelDTO( cadastroCidadeService.salvar(cidadeAtual) ) ;
			
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId ){
		
		try {
			cadastroCidadeService.excluir(cidadeId);
			
		} catch (CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		

	}
	

	
	
}
