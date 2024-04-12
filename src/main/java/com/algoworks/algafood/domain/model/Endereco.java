package com.algoworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
//@Embeddable Indica que a classe pode ser Incorporada dentro de outra Entidade( Classe ) É uma parte de uma outra
//entidade (Classe) Ex: Restaurante (é a classe) e (Endereço vai ser parte de Restuarante. 
//Mas não vai ter uma tabela no banco de dados com o Nome Endereço.)
@Embeddable 
public class Endereco {

	@Column(name = "endereco_cep")
	private String cep;
	
	@Column(name = "endereco_logradouro")
	private String logradouro;
	
	@Column(name = "endereco_numero")
	private String numero;
	
	@Column(name = "endereco_complemento")
	private String complemento;
	
	@Column(name = "endereco_bairro")
	private String bairro;
	
	@ManyToOne
	@JoinColumn(name = "endereco_cidade_id")
	private Cidade cidade;
	
	
}
