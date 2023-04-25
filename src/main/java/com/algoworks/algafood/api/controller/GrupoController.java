package com.algoworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algoworks.algafood.api.assembler.GrupoModelAssembler;
import com.algoworks.algafood.api.modelDTO.GrupoDTO;
import com.algoworks.algafood.domain.model.Grupo;
import com.algoworks.algafood.domain.model.repository.GrupoRepository;
import com.algoworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping( value= "/grupos" )
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private GrupoModelAssembler assemblerGrupo;
	
	@GetMapping
	public List<GrupoDTO> listarTodos() {	
		return assemblerGrupo.toColletionModel( grupoRepository.findAll()) ;
	}
	
	@GetMapping(value = "/{grupoId}")
	public GrupoDTO listarId(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		return assemblerGrupo.toModelDTO( grupo);
	}
	
	
}
