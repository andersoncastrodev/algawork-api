package com.algoworks.algafood.api.modelDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

	private Long id;

	private String nome;
	
	private String email;
	
	private LocalDateTime dataCadastro;
		
}
