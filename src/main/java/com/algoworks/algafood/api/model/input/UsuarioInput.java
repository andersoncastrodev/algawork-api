package com.algoworks.algafood.api.model.input;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String senha;
	
	private LocalDateTime dataCadastro;
}
