package com.algoworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algoworks.algafood.api.modelDTO.RestauranteDTO;
import com.algoworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	//Metodo que foi refatorado (Para Usar em varios locais diferentes). Extract.
	public RestauranteDTO toModelDTO(Restaurante restaurante) {
		
//		CozinhaDTO cozinhaDTO = new CozinhaDTO();
//		
//		cozinhaDTO.setId(restaurante.getCozinha().getId());
//		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
//		
//		//Converter Restaurante em RestauranteDTO.
//		RestauranteDTO restauranteDTO = new RestauranteDTO();
//		
//		restauranteDTO.setId(restaurante.getId());
//		restauranteDTO.setNome(restaurante.getNome());
//		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
//		restauranteDTO.setCozinha(cozinhaDTO);
		
//		return restauranteDTO;
		
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}
	
	//Conversão de uma lista de objetos para uma outra Lista de Objetos. 
	public List<RestauranteDTO> toColletionModel(List<Restaurante> restaurantes){
		
		return restaurantes.stream()
				.map(restaurante -> toModelDTO(restaurante))
				.collect(Collectors.toList());
	}
}
