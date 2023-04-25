package com.algoworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.algoworks.algafood.api.model.input.RestauranteInput;
import com.algoworks.algafood.domain.model.Cidade;
import com.algoworks.algafood.domain.model.Cozinha;
import com.algoworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	//Conversão de um ResturanteInput para um Restaurante.
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		
//		Restaurante restaurante = new Restaurante();
//		restaurante.setNome(restauranteInput.getNome());
//		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
//		
//		Cozinha cozinha = new Cozinha();
//		cozinha.setId(restauranteInput.getCozinha().getId());
//		
//		restaurante.setCozinha(cozinha);
//		
//		return restaurante;
		
		return modelMapper.map(restauranteInput, Restaurante.class);
		
	}
	
	//Metodo que copia do ResturanteInput para o Restaurante Entidade
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		 
		// Para Evitar o erro
		//org.hibernate.HibernateException: identifier of an instance of com.algoworks.algafood.domain.model.Cozinha was altered from 2 to 1	
		restaurante.setCozinha(new Cozinha());
		
		// Para Evitar o erro
		//org.hibernate.HibernateException: identifier of an instance of com.algoworks.algafood.domain.model.Restaurante was altered from 2 to 1	
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		 
		modelMapper.map(restauranteInput, restaurante);
	}
	
	
}
