package com.algoworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algoworks.algafood.api.model.input.CidadeInput;
import com.algoworks.algafood.domain.model.Cidade;
import com.algoworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDiassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInput cidadeInput ){
		
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	//Metodo que copia do CidadeInput para o Cidade Entidade
	public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
		 
		// Para Evitar o erro
		//org.hibernate.HibernateException: identifier of an instance of com.algoworks.algafood.domain.model.Cozinha was altered from 2 to 1
		
		cidade.setEstado(new Estado());
		 
		modelMapper.map(cidadeInput, cidade);
	}
}
