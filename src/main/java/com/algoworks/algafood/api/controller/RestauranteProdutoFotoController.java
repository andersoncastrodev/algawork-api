package com.algoworks.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algoworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algoworks.algafood.api.model.input.FotoProdutoInput;
import com.algoworks.algafood.api.modelDTO.FotoProdutoDTO;
import com.algoworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algoworks.algafood.domain.model.FotoProduto;
import com.algoworks.algafood.domain.model.Produto;
import com.algoworks.algafood.domain.service.CadastroProdutoService;
import com.algoworks.algafood.domain.service.CatalogoFotoPrudutoService;
import com.algoworks.algafood.domain.service.FotoStorageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CatalogoFotoPrudutoService catalogoFotoPrudutoService;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, 
		@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
	
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setProduto(produto);
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(arquivo.getContentType());
		fotoProduto.setTamanho(arquivo.getSize());
		fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());
		
	
		FotoProduto fotoSalva = catalogoFotoPrudutoService.salvar(fotoProduto, arquivo.getInputStream());
		
		return fotoProdutoModelAssembler.toModelDTO(fotoSalva);
		
		
		
		
// Teste do Uplado de arquivos		
//		var nomearquivo = UUID.randomUUID().toString()+"_"+ fotoProdutoInput.getArquivo().getOriginalFilename();
//		
//		var arquivoFoto = Path.of("/home/anderson/Imagens/UploadAlgaWorks",nomearquivo);
//		
//		System.out.println(fotoProdutoInput.getDescricao());
//		System.out.println(arquivoFoto);
//		System.out.println(fotoProdutoInput.getArquivo().getContentType());
//	
//	try {		
//		fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
//	
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
		
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
	    FotoProduto fotoProduto = catalogoFotoPrudutoService.buscarOuFalhar(restauranteId, produtoId);
	    
	    return fotoProdutoModelAssembler.toModelDTO(fotoProduto);
	}	
	
	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		
		try {
			
		FotoProduto fotoProduto = catalogoFotoPrudutoService.buscarOuFalhar(restauranteId, produtoId);
		
		InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(new InputStreamResource(inputStream));
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
