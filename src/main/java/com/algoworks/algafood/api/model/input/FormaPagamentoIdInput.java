package com.algoworks.algafood.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoIdInput {

	@NotNull
	private Long id;
}
