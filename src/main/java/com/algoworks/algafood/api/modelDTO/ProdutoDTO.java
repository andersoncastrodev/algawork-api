package com.algoworks.algafood.api.modelDTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {

	private String id;
	
	private String nome;
	
	private String descricao;
	
	private BigDecimal preco;
	
	private Boolean ativo;
}
