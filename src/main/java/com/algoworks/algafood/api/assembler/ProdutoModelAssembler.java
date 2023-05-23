package com.algoworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.algoworks.algafood.api.modelDTO.ProdutoDTO;
import com.algoworks.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoDTO toModelDTO(Produto produto) {
		
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	//Convert lista de Objeto Model para Lista de Objetos DTO.
	// Usado o metodo criado a cima "toModelDTO"
	public List<ProdutoDTO> toColletionModel(List<Produto> produtos){
		return produtos.stream()
				.map(produto -> toModelDTO(produto))
				.collect(Collectors.toList());
	}
}
