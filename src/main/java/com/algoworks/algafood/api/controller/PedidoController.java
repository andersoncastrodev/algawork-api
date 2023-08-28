package com.algoworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algoworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algoworks.algafood.api.assembler.PedidoModelAssembler;
import com.algoworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algoworks.algafood.api.model.input.PedidoInput;
import com.algoworks.algafood.api.modelDTO.PedidoDTO;
import com.algoworks.algafood.api.modelDTO.PedidoResumoDTO;
import com.algoworks.algafood.core.data.PageableTranslator;
import com.algoworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.algoworks.algafood.domain.filter.PedidoFilter;
import com.algoworks.algafood.domain.model.Pedido;
import com.algoworks.algafood.domain.model.Usuario;
import com.algoworks.algafood.domain.repository.PedidoRepository;
import com.algoworks.algafood.domain.service.EmissaoPedidoService;
import com.algoworks.algafood.infrastructere.repository.spec.PedidoSpec;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	
	//Usando o @JsonFilter("pedidoFilter") para consultar na API mais dinamicas ainda. Depis tipo um sql no banco de dados.
//	@GetMapping
//	public MappingJacksonValue listar(){
//		List<Pedido> todosPedidos = pedidoRepository.findAll();
//		List<PedidoResumoDTO> pedidosDTO = pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosDTO);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept("codigo","valorTotal"));
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//	}
	
//	@GetMapping
//	public List<PedidoResumoDTO> listar(){
//		List<Pedido> todosPedidos = pedidoRepository.findAll();
//		return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
//	}
	
	@GetMapping
	public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable){
		
		Page<Pedido> pedidoPage = pedidoRepository.findAll(PedidoSpec.usandoFiltro(filtro), pageable);
		
		List<PedidoResumoDTO> pedidosResumoDTO = pedidoResumoModelAssembler.toCollectionModel(pedidoPage.getContent());
		
		Page<PedidoResumoDTO> pedidosResumoDTOPage = new PageImpl<>(pedidosResumoDTO, pageable, pedidoPage.getTotalElements());
	
		return pedidosResumoDTOPage;
	}

	
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
		return pedidoModelAssembler.toModel(pedido);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
	    try {	
	        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

	        // TODO pegar usuário autenticado
	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(1L);

	        novoPedido = emissaoPedidoService.emitir(novoPedido);

	        return pedidoModelAssembler.toModel(novoPedido);
	        
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}	
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		
		//Mapeamento DE para 
		var mapeamento = Map.of(
				"codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
			);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	} 
	
	
}
