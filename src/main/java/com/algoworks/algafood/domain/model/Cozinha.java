package com.algoworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;
import com.algoworks.algafood.grupos.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true )
@Entity	
public class Cozinha {

	//Anotacao para usar o Groups 
	@NotNull(groups = Groups.CozinhaId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column( nullable = false)
	private String nome;

	//Usar para evitar o loopInfinito e Ocultar esse atributo na consulta da API.
	@JsonIgnore
	//Aqui faz o relacionamento Bidirecional.
	@OneToMany(mappedBy = "cozinha")
	private List<Restaurante> restaurantes = new ArrayList<>();
	
}
