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

import com.algoworks.algafood.api.assembler.GrupoInputDisassembler;
import com.algoworks.algafood.api.assembler.GrupoModelAssembler;
import com.algoworks.algafood.api.model.input.GrupoInput;
import com.algoworks.algafood.api.modelDTO.GrupoDTO;
import com.algoworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.model.Grupo;
import com.algoworks.algafood.domain.model.repository.GrupoRepository;
import com.algoworks.algafood.domain.service.CadastroGrupoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private CadastroGrupoService cadastroGrupoService;

	@Autowired
	private GrupoModelAssembler grupoAssemblerGrupo;

	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@GetMapping
	public List<GrupoDTO> listarTodos() {
		return grupoAssemblerGrupo.toColletionModel(grupoRepository.findAll());
	}

	@GetMapping(value = "/{grupoId}")
	public GrupoDTO listarId(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		return grupoAssemblerGrupo.toModelDTO(grupo);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {

		try {

			Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

			return grupoAssemblerGrupo.toModelDTO(cadastroGrupoService.salvar(grupo));

		} catch (GrupoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}

	}
	
	@PutMapping("/{grupoId}")
	public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
	
		try {
			
			 Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);
			
			 grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
			 
			return grupoAssemblerGrupo.toModelDTO( cadastroGrupoService.salvar(grupoAtual) );
	
		} catch (GrupoNaoEncontradoException e) {
			 throw new NegocioException(e.getMessage());
		}

	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		
		try {
			
			cadastroGrupoService.excluir(grupoId);
			
		} 
		catch (GrupoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
}
