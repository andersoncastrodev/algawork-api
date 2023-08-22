package com.algoworks.algafood.domain.model.repository.filter;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter {
	
	private Long clienteId;
	private Long restauranteId;
	private OffsetDateTime dataCriacaoInicio;
	private OffsetDateTime dataCriacaoFim;

}
