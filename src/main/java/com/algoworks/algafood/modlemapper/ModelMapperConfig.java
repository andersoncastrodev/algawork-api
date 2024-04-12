package com.algoworks.algafood.modlemapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algoworks.algafood.api.model.input.ItemPedidoInput;
import com.algoworks.algafood.api.modelDTO.EnderecoDTO;
import com.algoworks.algafood.domain.model.Endereco;
import com.algoworks.algafood.domain.model.ItemPedido;

//Use o configuration
@Configuration
public class ModelMapperConfig {

	//Criando uma instancia de ModelMapper dentro do contexto do SPRING.
	//Sendo que ModelMapper é uma .jar biblioteca de Terceiro.
	@Bean
	public ModelMapper modelMapper() {
		
		 ModelMapper modelMapper = new ModelMapper();
		
		TypeMap<Endereco, EnderecoDTO> enderecoToEnderecoModelMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		//Configuração especiar para iten pedido
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
	    .addMappings(mapper -> mapper.skip(ItemPedido::setId));  
		
		enderecoToEnderecoModelMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(), 
				(dest, value) -> dest.getCidade().setEstado(value));
		
		return modelMapper;
	}
	
}
