package com.algoworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algoworks.algafood.api.modelDTO.UsuarioDTO;
import com.algoworks.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO toModelDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	//Convert uma Lista complepa.
	public List<UsuarioDTO> toColletionModel(List<Usuario> usuarios){
		return usuarios.stream()
				.map(usuario -> toModelDTO(usuario))
				.collect(Collectors.toList());
	}
	
	
	
}
