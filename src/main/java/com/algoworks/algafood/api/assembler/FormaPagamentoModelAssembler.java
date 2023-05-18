package com.algoworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algoworks.algafood.api.modelDTO.FormaPagamentoDTO;
import com.algoworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoDTO toModelDTO(FormaPagamento formaPagamento){
		
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	//Conversão de uma lista de objetos para uma outra Lista de Objetos. 
	public List<FormaPagamentoDTO> toColletionModel(Collection<FormaPagamento> formaPagamentos){
	return formaPagamentos.stream()
	.map(formapagamento -> toModelDTO(formapagamento))
	.collect(Collectors.toList());
	}

}
