package com.algoworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.algoworks.algafood.api.modelDTO.FotoProdutoDTO;
import com.algoworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoDTO toModelDTO(FotoProduto foto){
		
		return modelMapper.map(foto, FotoProdutoDTO.class);
	}
	
}
