package com.algoworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;
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
import com.algoworks.algafood.api.model.CozinhaDTO;
import com.algoworks.algafood.api.model.RestauranteDTO;
import com.algoworks.algafood.api.model.input.RestauranteInput;
import com.algoworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algoworks.algafood.domain.model.Cozinha;
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
	
	
	@GetMapping
	public List<RestauranteDTO> listar(){
		return toColletionModel( restauranteRepository.findAll() );
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		
		//return cadastroResturanteService.buscaOuFalhar(restauranteId);
		Restaurante restaurante = cadastroResturanteService.buscaOuFalhar(restauranteId);
		
		return toModelDTO(restaurante);
	}


	@PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		
		try {
			
			//Usando o metodo que que conver RestauranteInput para Restaurante
			Restaurante restaurante =  toDomainObject(restauranteInput);
			
			return toModelDTO(cadastroResturanteService.salvar(restaurante) );
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,@RequestBody  @Valid RestauranteInput restauranteInput ) {
		
		//Usando o metodo que que conver RestauranteInput para Restaurante
		Restaurante restaurante =  toDomainObject(restauranteInput);
		
		//Codigo Refatorado
		Restaurante restauranteAtual = cadastroResturanteService.buscaOuFalhar(restauranteId);
		
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento","endereco","dataCadastro", "produtos");
		
		try {
			
			return toModelDTO( cadastroResturanteService.salvar(restauranteAtual) );
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
		
		//Codigo Antigo
//		try {
//			
//			Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
//			
//			if( restauranteAtual.isPresent() ) {
//
//				BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id", "formasPagamento",
//					"endereco","dataCadastro", "produtos");
//				
//				Restaurante restauranteSalvo = cadastroResturanteService.salvar(restauranteAtual.get());
//				
//				return ResponseEntity.ok(restauranteSalvo);
//			}
//		
//			return ResponseEntity.notFound().build(); //Caso no encontre o Id do resturante.
//			
//		}catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage()); //Se o codigo NÃO Existir
//		}
	
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
	
	//Usando a atualização tipo PATCH que atualizar um objeto somente um atributo.
	
//	@PatchMapping("/{restauranteId}")
//	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
//			@RequestBody Map<String, Object> campos) {
//		Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);
//		
//		if (restauranteAtual == null) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		merge(campos, restauranteAtual);
//		
//		return atualizar(restauranteId, restauranteAtual);
//	}
//
//	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//		
//		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//			field.setAccessible(true);
//			
//			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//			
////			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
//			
//			ReflectionUtils.setField(field, restauranteDestino, novoValor);
//		});
//	}
	
	//Metodo que foi refatorado (Para Usar em varios locais diferentes). Extract.
	private RestauranteDTO toModelDTO(Restaurante restaurante) {
		
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		//Converter Restaurante em RestauranteDTO.
		RestauranteDTO restauranteDTO = new RestauranteDTO();
		
		restauranteDTO.setId(restaurante.getId());
		restauranteDTO.setNome(restaurante.getNome());
		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteDTO.setCozinha(cozinhaDTO);
		
		return restauranteDTO;
	}
	
	//Conversão de uma lista de objetos para uma outra Lista de Objetos. 
	private List<RestauranteDTO> toColletionModel(List<Restaurante> restaurantes){
		
		return restaurantes.stream()
				.map(restaurante -> toModelDTO(restaurante))
				.collect(Collectors.toList());
	}
	
	//Conversão de um ResturanteInput para um Restaurante.
	private Restaurante toDomainObject(RestauranteInput restauranteInput) {
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		
		restaurante.setCozinha(cozinha);
		
		return restaurante;
		
	}
	
}
