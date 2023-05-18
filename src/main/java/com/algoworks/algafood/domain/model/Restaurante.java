package com.algoworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algoworks.algafood.grupos.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@NotNull
	//@NotEmpty
	//@NotBlank(groups = Groups.CadastroRestaurante.class) //Anotacao para usar o Groups 
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	//@DecimalMin("1")
	//@PositiveOrZero(groups = Groups.CadastroRestaurante.class)
	@NotNull
	@PositiveOrZero
	@Column(name="taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	//@NotNull(groups = Groups.CadastroRestaurante.class) //Anotacao para usar o Groups
	@NotNull
	@ManyToOne 
	@JoinColumn(name="cozinha_id" , nullable = false)
	private Cozinha cozinha;
	
	//@Embedded Diz que esse atributo é Incorpado: de Uma classe Incorporável 
	//Ou seja ela vem com classe Anota com o @Embeddable OBS: Mas os atribuitos da classe Endereço
	//Será criado na tabela Restaurante.
	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	private Boolean ativo = Boolean.TRUE;
	
	@JsonIgnore
	@CreationTimestamp //Gera a Data e Hora atual.
	@Column(nullable = false)
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp //Gera a Data e Hora quando for Atualizada.
	@Column(nullable = false)
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
	joinColumns = @JoinColumn(name = "restaurante_id"), 
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")  )
	private Set<FormaPagamento> formasPagamento = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	public void ativar() {
		setAtivo(true);
	}
	public void inativar() {
		setAtivo(false);
	}
	
	public boolean removeFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}

	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
}
