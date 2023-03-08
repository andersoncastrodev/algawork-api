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

import com.algoworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algoworks.algafood.domain.model.Restaurante;
import com.algoworks.algafood.domain.model.repository.RestauranteRepository;
import com.algoworks.algafood.domain.service.CadastroResturanteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroResturanteService cadastroResturanteService;
	
	
	@GetMapping
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId) {
		//Refatorando
		return cadastroResturanteService.buscaOuFalhar(restauranteId);
		
		//Usando a expresso lambida 
//		Restaurante restaurante = restauranteRepository.findById(restauranteId)
//							      .orElseThrow( ()-> new EntidadeNaoEncontradaException("mensagem") );
//								
//	    return restaurante;	
		
		
		//Codigo Antigo
//	   Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
//		
//		if (restaurante.isPresent()) {
//			
//			return ResponseEntity.ok(restaurante.get());
//		}
//		
//		return ResponseEntity.notFound().build();
	}
	
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){		
//		try {	
//			restaurante = cadastroResturanteService.salvar(restaurante);
//			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
//			
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage()); //Se o codigo NÃO Existir
//		}
//	}
	
	@PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		
		try {
			
			return cadastroResturanteService.salvar(restaurante);
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante ) {
		
		//Codigo Refatorado
		Restaurante restauranteAtual = cadastroResturanteService.buscaOuFalhar(restauranteId);
		
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento","endereco","dataCadastro", "produtos");
		
		try {
			
			return cadastroResturanteService.salvar(restauranteAtual);
			
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
}
