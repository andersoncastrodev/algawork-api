package com.algoworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.algoworks.algafood.api.modelDTO.CidadeDTO;
import com.algoworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public CidadeDTO toModelDTO(Cidade cidade) {		
		return modelMapper.map(cidade, CidadeDTO.class);
	}


	//Conversão de uma lista de objetos para uma outra Lista de Objetos. 
	public List<CidadeDTO> toColletionModel(List<Cidade> cidades){
	
		return cidades.stream()
				.map(cidade -> toModelDTO(cidade))
				.collect(Collectors.toList());
	}
}
