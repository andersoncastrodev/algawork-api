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

import com.algoworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algoworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algoworks.algafood.api.model.RestauranteDTO;
import com.algoworks.algafood.api.model.input.RestauranteInput;
import com.algoworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algoworks.algafood.domain.model.Restaurante;
import com.algoworks.algafood.domain.model.repository.RestauranteRepository;
import com.algoworks.algafood.domain.service.CadastroResturanteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroResturanteService cadastroResturanteService;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	@GetMapping
	public List<RestauranteDTO> listar(){
		return restauranteModelAssembler.toColletionModel( restauranteRepository.findAll() );
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		
		//return cadastroResturanteService.buscaOuFalhar(restauranteId);
		Restaurante restaurante = cadastroResturanteService.buscaOuFalhar(restauranteId);
		
		return restauranteModelAssembler.toModelDTO(restaurante);
	}


	@PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		
		try {
			
			//Usando o metodo que que conver RestauranteInput para Restaurante
			Restaurante restaurante =  restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			return restauranteModelAssembler.toModelDTO(cadastroResturanteService.salvar(restaurante) );
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,@RequestBody  @Valid RestauranteInput restauranteInput ) {
		
		//Usando o metodo que que conver RestauranteInput para Restaurante
		//Restaurante restaurante =  restauranteInputDisassembler.toDomainObject(restauranteInput);
		
		//Codigo Refatorado
		Restaurante restauranteAtual = cadastroResturanteService.buscaOuFalhar(restauranteId);
		
		restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
		
		//BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento","endereco","dataCadastro", "produtos");
		
		try {
			
			return restauranteModelAssembler.toModelDTO( cadastroResturanteService.salvar(restauranteAtual) );
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId){	
		
		try {
			
			cadastroResturanteService.remover(restauranteId);
			
		} catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
			
	}
	

}
