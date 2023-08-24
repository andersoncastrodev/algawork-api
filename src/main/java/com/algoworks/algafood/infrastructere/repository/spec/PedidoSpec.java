package com.algoworks.algafood.infrastructere.repository.spec;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import com.algoworks.algafood.domain.model.Pedido;
import com.algoworks.algafood.domain.model.repository.filter.PedidoFilter;

import jakarta.persistence.criteria.Predicate;

public class PedidoSpec {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
		return (root, query, builder)->{
				
			//Resolvendo o N + 1
			root.fetch("restaurante").fetch("cozinha");
			root.fetch("restaurante").fetch("endereco").fetch("cidade").fetch("estado");
			root.fetch("cliente");
			
			var predicates = new ArrayList<Predicate>();
			
			if(filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente").get("id"), filtro.getClienteId()));
			}
			
			if(filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
			}
			
			if(filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
			}
			
			if(filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
