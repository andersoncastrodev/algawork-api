package com.algoworks.algafood.api.model.input;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@NotNull
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
}
