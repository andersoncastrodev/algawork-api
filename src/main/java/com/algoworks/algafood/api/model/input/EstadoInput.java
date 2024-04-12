package com.algoworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoInput {

	@NotNull
	private String nome;
}
