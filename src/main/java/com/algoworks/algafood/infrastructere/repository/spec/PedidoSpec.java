package com.algoworks.algafood.infrastructere.repository.spec;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import com.algoworks.algafood.domain.filter.PedidoFilter;
import com.algoworks.algafood.domain.model.Pedido;

import javax.persistence.criteria.Predicate;

public class PedidoSpec {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
		return (root, query, builder)->{
			
			
			if(Pedido.class.equals(query.getResultType())) {
				
				//Resolvendo o N + 1
				root.fetch("restaurante").fetch("cozinha");
				root.fetch("restaurante").fetch("endereco").fetch("cidade").fetch("estado");
				root.fetch("cliente");		
			}

			 ArrayList<Predicate> predicates = new ArrayList<Predicate>();
			
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
