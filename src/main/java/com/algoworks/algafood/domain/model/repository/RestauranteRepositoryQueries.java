package com.algoworks.algafood.domain.model.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algoworks.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}