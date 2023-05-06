package com.algoworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algoworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algoworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algoworks.algafood.api.model.input.UsuarioInput;
import com.algoworks.algafood.api.modelDTO.UsuarioDTO;
import com.algoworks.algafood.domain.model.Usuario;
import com.algoworks.algafood.domain.model.repository.UsuarioRepository;
import com.algoworks.algafood.domain.service.CadastroUsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioModelAssembler usuarioAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@GetMapping
	public List<UsuarioDTO> listaTodos() {
		
		return usuarioAssembler.toColletionModel(usuarioRepository.findAll());		
	}
	
	@GetMapping
	@RequestMapping("/{usuarioId}")
	public UsuarioDTO buscar(@PathVariable Long usuarioId) {
		
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		
		return usuarioAssembler.toModelDTO(usuario)  ;
	}
	
	@PostMapping
	public UsuarioDTO adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
	
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		
		return usuarioAssembler.toModelDTO( cadastroUsuarioService.salvar(usuario));
	}
	
	
}
