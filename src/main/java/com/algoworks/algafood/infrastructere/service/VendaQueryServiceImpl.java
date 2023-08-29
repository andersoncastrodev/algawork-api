package com.algoworks.algafood.infrastructere.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.algoworks.algafood.domain.filter.VendaDiariaFilter;
import com.algoworks.algafood.domain.model.Pedido;
import com.algoworks.algafood.domain.model.dto.VendaDiaria;
import com.algoworks.algafood.domain.service.VendaQueryService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


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
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
		
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		
		// Preparando a data da consulta SQL
		var funcitionDateDataCriacao = builder.function(
				"date",
				Date.class, 
				root.get("dataCriacao"));
		
		var selection = builder.construct(VendaDiaria.class
				,funcitionDateDataCriacao // Data do SQL
				, builder.count(root.get("id"))  //Função count SQL
				, builder.sum(root.get("valorTotal")) //Função sum SQL
			);
		
		query.select(selection).groupBy(funcitionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

}
