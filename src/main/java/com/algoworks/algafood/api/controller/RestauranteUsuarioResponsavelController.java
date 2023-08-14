package com.algoworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algoworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algoworks.algafood.api.modelDTO.UsuarioDTO;
import com.algoworks.algafood.domain.model.Restaurante;
import com.algoworks.algafood.domain.service.CadastroResturanteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

	@Autowired
	public CadastroResturanteService cadastroResturanteService;
	
	@Autowired
	public UsuarioModelAssembler usuarioModelAssembler;
	
	@GetMapping
	public List<UsuarioDTO> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroResturanteService.buscarOuFalhar(restauranteId);
		return usuarioModelAssembler.toColletionModel(restaurante.getResponsaveis());
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroResturanteService.desassociarResponsavel(restauranteId, usuarioId);
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroResturanteService.associarResponsavel(restauranteId, usuarioId);
	}
	
}
