package com.algoworks.algafood.core.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class PageableTranslator {

	//Classe de Utilitaria 
	public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {
		List<Order> orders = pageable.getSort().stream()
				.filter(order -> fieldsMapping.containsKey(order.getProperty()))
				.map(order -> new Sort.Order(order.getDirection(), 
						fieldsMapping.get(order.getProperty())))
				.collect(Collectors.toList());
								
			return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
					Sort.by(orders));
	}
}
