package com.algoworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algoworks.algafood.api.modelDTO.EstadoDTO;
import com.algoworks.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public EstadoDTO toModelDTO(Estado estado){
		
		return modelMapper.map(estado, EstadoDTO.class);
	}
	
	//Conversão de uma lista de objetos para uma outra Lista de Objetos. 
	public List<EstadoDTO> toColletionModel(List<Estado> estados){
	
		List<EstadoDTO> lista = estados.stream()
				.map(estado -> toModelDTO(estado))
				.collect(Collectors.toList());
		
		return lista ;
	}
	
}
