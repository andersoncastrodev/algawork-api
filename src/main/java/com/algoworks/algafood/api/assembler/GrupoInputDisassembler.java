package com.algoworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algoworks.algafood.api.model.input.GrupoInput;
import com.algoworks.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public Grupo toDomainObject(GrupoInput grupoInput) {
		
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	//Usando para o Atualizar
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		
		modelMapper.map(grupoInput, grupo);
	}
}
