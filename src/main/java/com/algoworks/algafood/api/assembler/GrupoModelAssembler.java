package com.algoworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algoworks.algafood.api.modelDTO.GrupoDTO;
import com.algoworks.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoDTO toModelDTO(Grupo grupo) {
		
		return modelMapper.map(grupo, GrupoDTO.class);
	}
	
	//Convert lista de Objeto Model para Lista de Objetos DTO.
	// Usado o metodo criado a cima "toModelDTO"
	public List<GrupoDTO> toColletionModel(List<Grupo> grupos){
		return grupos.stream()
				.map(grupo -> toModelDTO(grupo))
				.collect(Collectors.toList());
	}
}
