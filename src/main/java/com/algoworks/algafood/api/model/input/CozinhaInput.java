package com.algoworks.algafood.api.model.input;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaInput {

	@NotBlank
	private String nome;

}
