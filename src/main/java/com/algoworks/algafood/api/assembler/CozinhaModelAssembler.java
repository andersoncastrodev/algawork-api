package com.algoworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.algoworks.algafood.api.modelDTO.CozinhaDTO;
import com.algoworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler{

	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaDTO toModelDTO(Cozinha cozinha){
		
		return modelMapper.map(cozinha, CozinhaDTO.class);
	}
	
	//Conversão de uma lista de objetos para uma outra Lista de Objetos. 
	public List<CozinhaDTO> toColletionModel(List<Cozinha> cozinhas){
	
		List<CozinhaDTO> lista = cozinhas.stream()
				.map(cozinha -> toModelDTO(cozinha))
				.collect(Collectors.toList());
		
		return lista ;
	}
	
}
