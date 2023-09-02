package com.algoworks.algafood.api.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import javax.print.attribute.standard.Media;
import javax.sound.midi.Patch;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algoworks.algafood.api.model.input.FotoProdutoInput;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId, 
		@PathVariable Long produtoId, FotoProdutoInput fotoProdutoInput) {
	
	try {	
		
		var nomearquivo = UUID.randomUUID().toString()+"_"+ fotoProdutoInput.getArquivo().getOriginalFilename();
		
		var arquivoFoto = Path.of("/home/anderson/Imagens/UploadAlgaWorks",nomearquivo);
		
		System.out.println(fotoProdutoInput.getDescricao());
		System.out.println(arquivoFoto);
		System.out.println(fotoProdutoInput.getArquivo().getContentType());
		
		fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
	
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	}
}
