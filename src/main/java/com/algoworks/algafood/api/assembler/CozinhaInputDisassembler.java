package com.algoworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algoworks.algafood.api.model.input.CozinhaInput;
import com.algoworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInput cozinhaInput ){
		
		return modelMapper.map(cozinhaInput, Cozinha.class);
	}
	
	//Metodo que copia do CidadeInput para o Cidade Entidade
	public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
		 
		// Para Evitar o erro
		//org.hibernate.HibernateException: identifier of an instance of com.algoworks.algafood.domain.model.Cozinha was altered from 2 to 1
		
		//cozinha.setRestaurantes(new Restaurante());
		 
		modelMapper.map(cozinhaInput, cozinha);
	}
}
