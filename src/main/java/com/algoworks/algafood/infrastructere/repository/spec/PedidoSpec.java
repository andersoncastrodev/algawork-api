package com.algoworks.algafood.infrastructere.repository.spec;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import com.algoworks.algafood.domain.model.Pedido;
import com.algoworks.algafood.domain.model.repository.filter.PedidoFilter;

import jakarta.persistence.criteria.Predicate;

public class PedidoSpec {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
		return (root, query, builder)->{

			// Problema N + 1		
//			root.fetch("restaurante").fetch("cozinha");
//			root.fetch("cliente");
			
			var predicates = new ArrayList<Predicate>();
			
			if(filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
