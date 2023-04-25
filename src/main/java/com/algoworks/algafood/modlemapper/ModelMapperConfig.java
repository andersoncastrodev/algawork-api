package com.algoworks.algafood.modlemapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.algoworks.algafood.api.modelDTO.EnderecoDTO;
import com.algoworks.algafood.domain.model.Endereco;

//Use o configuration
@Configuration
public class ModelMapperConfig {

	//Criando uma instancia de ModelMapper dentro do contexto do SPRING.
	//Sendo que ModelMapper é uma .jar biblioteca de Terceiro.
	@Bean
	public ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
		
		var enderecoToEnderecoModelMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoModelMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(), 
				(dest, value) -> dest.getCidade().setEstado(value));
		
		return modelMapper;
	}
	
}
