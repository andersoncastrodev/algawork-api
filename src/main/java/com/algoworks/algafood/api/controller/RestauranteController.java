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
import com.algoworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algoworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algoworks.algafood.api.model.input.RestauranteInput;
import com.algoworks.algafood.api.modelDTO.RestauranteDTO;
import com.algoworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algoworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algoworks.algafood.domain.model.Restaurante;
import com.algoworks.algafood.domain.repository.RestauranteRepository;
import com.algoworks.algafood.domain.service.CadastroResturanteService;
import com.algoworks.algafood.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import javax.validation.Valid;

//@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroResturanteService cadastroResturanteService ;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	@JsonView(RestauranteView.Resumo.class) //Para resumir a apresentacao do recurso.
	@GetMapping
	public List<RestauranteDTO> listar(){
		return restauranteModelAssembler.toColletionModel( restauranteRepository.findAll() );
	}
	
	//Metodo de Permitir o CORS atravez do Cabelhaçario
	
//	@JsonView(RestauranteView.Resumo.class) //Para resumir a apresentacao do recurso.
//	@GetMapping
//	public ResponseEntity<List<RestauranteDTO>> listar(){
//		
//		List<RestauranteDTO> restauranteDTO = restauranteModelAssembler.toColletionModel( restauranteRepository.findAll());
//		
//		return ResponseEntity.ok()
//				.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"http://127.0.0.1:5500")
//				.body(restauranteDTO);
//	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		
		//return cadastroResturanteService.buscaOuFalhar(restauranteId);
		Restaurante restaurante = cadastroResturanteService.buscarOuFalhar(restauranteId);
		
		return restauranteModelAssembler.toModelDTO(restaurante);
	}

	@PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		
		try {
			
			//Usando o metodo que que conver RestauranteInput para Restaurante
			Restaurante restaurante =  restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			return restauranteModelAssembler.toModelDTO(cadastroResturanteService.salvar(restaurante) );
			
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}	
	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,@RequestBody  @Valid RestauranteInput restauranteInput ) {
		
		//Usando o metodo que que conver RestauranteInput para Restaurante
		//Restaurante restaurante =  restauranteInputDisassembler.toDomainObject(restauranteInput);
		
		//Codigo Refatorado
		Restaurante restauranteAtual = cadastroResturanteService.buscarOuFalhar(restauranteId);
		
		restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
		
		//BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento","endereco","dataCadastro", "produtos");
		
		try {
			
			return restauranteModelAssembler.toModelDTO( cadastroResturanteService.salvar(restauranteAtual) );
			
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
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
	
	//PUT /restaurante/{id}/ativo
	//DELETE /restaurante/{id}/ativo
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		
		cadastroResturanteService.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		
		cadastroResturanteService.inativar(restauranteId);
	}
	
	
	// PUT /restaurantes/ativacoes
	// [1,3,4,5] - Lista do ids do restaurantes.
	
	// DELETE /restaurantes/ativacoes
	// [1,3,4,5] - Lista do ids do restaurantes.
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultipos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroResturanteService.ativar(restauranteIds);
		}catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void InativarMultipos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroResturanteService.inativar(restauranteIds);
		}catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	               
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId){
		cadastroResturanteService.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId){
		cadastroResturanteService.fechar(restauranteId);
	}
	
}
