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

import com.algoworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algoworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algoworks.algafood.api.model.input.FormaPagamentoInput;
import com.algoworks.algafood.api.modelDTO.FormaPagamentoDTO;
import com.algoworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algoworks.algafood.domain.model.FormaPagamento;
import com.algoworks.algafood.domain.model.repository.FormaPagamentoRepository;
import com.algoworks.algafood.domain.service.CadastroFormaPagamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/formapagamento")
public class FormaPagamentoController {

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> listar(){
	
		return formaPagamentoModelAssembler.toColletionModel(formaPagamentoRepository.findAll()); 
	}
	
	@GetMapping("/{formapagamentoId}")
	public FormaPagamentoDTO buscar(@PathVariable Long formapagamentoId) {
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscaOuFalha(formapagamentoId);
		return formaPagamentoModelAssembler.toModelDTO(formaPagamento);
	}
	
	@PostMapping()
	@ResponseStatus(value = HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
		
		return formaPagamentoModelAssembler.toModelDTO( cadastroFormaPagamentoService.salvar(formaPagamento) );
	}
	
	@PutMapping("/{formapagamentoId}")
	public FormaPagamentoDTO atualizar(@PathVariable Long formapagamentoId,@RequestBody  @Valid FormaPagamentoInput formaPagamentoInput) {
		
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscaOuFalha(formapagamentoId);
		
		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);
		
		
		try {

			return formaPagamentoModelAssembler.toModelDTO(cadastroFormaPagamentoService.salvar(formaPagamento));
			
		}catch (FormaPagamentoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	
	}
	
	@DeleteMapping("/{formapagamentoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formapagamentoId) {
		
		try {
			
			cadastroFormaPagamentoService.excluir(formapagamentoId);
			
		} catch (FormaPagamentoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
			
	}
	
}
