package com.algoworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.algoworks.algafood.api.modelDTO.PedidoDTO;
import com.algoworks.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoDTO toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoDTO.class);
	}

	public List<PedidoDTO> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
    
}
