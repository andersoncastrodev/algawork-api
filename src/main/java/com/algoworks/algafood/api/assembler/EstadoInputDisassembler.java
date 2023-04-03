package com.algoworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.algoworks.algafood.api.model.input.EstadoInput;
import com.algoworks.algafood.domain.model.Estado;

@Component
public class EstadoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomainObject(EstadoInput estadoInput ){
		
		return modelMapper.map(estadoInput, Estado.class);
	}
	
	//Metodo que copia do CidadeInput para o Cidade Entidade
	public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
		 
		// Para Evitar o erro
		//org.hibernate.HibernateException: identifier of an instance of com.algoworks.algafood.domain.model.Cozinha was altered from 2 to 1
		
		//cozinha.setRestaurantes(new Restaurante());
		 
		modelMapper.map(estadoInput, estado);
	}
}
