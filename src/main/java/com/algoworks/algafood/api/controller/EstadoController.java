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

import com.algoworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.model.Estado;
import com.algoworks.algafood.domain.model.repository.EstadoRepository;
import com.algoworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	
	@GetMapping
	public List<Estado> listar(){
		
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{estadosId}")
	public Estado buscar(@PathVariable Long estadosId) {	
		
		try {
			
			return cadastroEstadoService.buscaOuFalha(estadosId);

		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		
		return cadastroEstadoService.salvar(estado);
	}
	
	@PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable Long estadoId, @RequestBody Estado estado ){
		
		Estado estadoAtual = cadastroEstadoService.buscaOuFalha(estadoId);
		
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		
		try {
			
			return cadastroEstadoService.salvar(estadoAtual);
			
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
