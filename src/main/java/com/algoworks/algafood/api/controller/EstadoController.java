package com.algoworks.algafood.api.controller;

import java.util.List;

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

import com.algoworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algoworks.algafood.api.assembler.EstadoModelAssembler;
import com.algoworks.algafood.api.model.input.EstadoInput;
import com.algoworks.algafood.api.modelDTO.EstadoDTO;
import com.algoworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.model.Estado;
import com.algoworks.algafood.domain.model.repository.EstadoRepository;
import com.algoworks.algafood.domain.service.CadastroEstadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Autowired
	private EstadoModelAssembler estadoAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoDisassembler;
	
	
	@GetMapping
	public List<EstadoDTO> listar(){
		
		return estadoAssembler.toColletionModel( estadoRepository.findAll() ) ;
	}
	
	@GetMapping("/{estadosId}")
	public EstadoDTO buscar(@PathVariable Long estadosId) {	
		
		try {
			
			return estadoAssembler.toModelDTO( cadastroEstadoService.buscaOuFalha(estadosId) );

		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
	
		Estado estado = estadoDisassembler.toDomainObject(estadoInput);
				
		return estadoAssembler.toModelDTO(cadastroEstadoService.salvar(estado) );
	}
	
	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId,@RequestBody @Valid EstadoInput estadoInput ){
		
		Estado estadoAtual = cadastroEstadoService.buscaOuFalha(estadoId);
		
		estadoDisassembler.copyToDomainObject(estadoInput, estadoAtual);
		//BeanUtils.copyProperties(estado, estadoAtual, "id");
		
		try {
			
			return estadoAssembler.toModelDTO( cadastroEstadoService.salvar(estadoAtual) );
			
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
 
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {

		cadastroEstadoService.excluir(estadoId);

	}
	
	
}
