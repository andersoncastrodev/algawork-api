package com.algoworks.algafood.infrastructere.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.algoworks.algafood.domain.filter.VendaDiariaFilter;
import com.algoworks.algafood.domain.model.Pedido;
import com.algoworks.algafood.domain.model.StatusPedido;
import com.algoworks.algafood.domain.model.dto.VendaDiaria;
import com.algoworks.algafood.domain.service.VendaQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;


@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager manager;
	
	/* Montando esse select:
	 * 
	 * select date(p.data_criacao) as data_criacao,
	 * 		count(p.id) as total_vendas,
	 * 		sum(p.valor_total) as total_faturado
	 * from pedido p
	 * group by date(p.data_criacao)
	 * 
	 */
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		
		//Adicionando os predicades
		var predicates = new ArrayList<Predicate>();
		
		var functionConvertTzDataCriacao = builder.function(
				"convert_tz"
				,Date.class
				,root.get("dataCriacao")
				,builder.literal("+00:00")
				,builder.literal(timeOffset)
				);
		
		// Preparando a data da consulta SQL
		var functionDateDataCriacao = builder.function(
				"date",
				Date.class, 
				functionConvertTzDataCriacao);
		
	
		var selection = builder.construct(VendaDiaria.class
				,functionDateDataCriacao // Data do SQL
				, builder.count(root.get("id"))  //Função count SQL
				, builder.sum(root.get("valorTotal")) //Função sum SQL
			);
		
		if (filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
		}
	      
		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
					filtro.getDataCriacaoInicio()));
		}

		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
					filtro.getDataCriacaoFim()));
		}
	      
		predicates.add(root.get("status").in(
				StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		query.select(selection).groupBy(functionDateDataCriacao);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

}
