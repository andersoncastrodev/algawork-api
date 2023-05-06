package com.algoworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algoworks.algafood.api.model.input.UsuarioInput;
import com.algoworks.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	//Convert de UsuarioInput para UsuarioModel
	public Usuario toDomainObject(UsuarioInput usuarioInput) {
		
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	//Metodo com copia de UsuarioInput para UsuarioModel
	public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
	
		modelMapper.map(usuarioInput, usuario);
	}
	
}
