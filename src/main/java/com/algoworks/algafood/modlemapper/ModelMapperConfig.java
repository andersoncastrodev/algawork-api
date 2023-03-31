package com.algoworks.algafood.modlemapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Use o configuration
@Configuration
public class ModelMapperConfig {

	//Criando uma instancia de ModelMapper dentro do contexto do SPRING.
	//Sendo que ModelMapper é uma .jar biblioteca de Terceiro.
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
