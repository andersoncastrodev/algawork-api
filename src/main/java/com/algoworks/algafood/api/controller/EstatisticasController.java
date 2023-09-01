package com.algoworks.algafood.api.controller;

import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.algoworks.algafood.domain.filter.VendaDiariaFilter;
import com.algoworks.algafood.domain.model.dto.VendaDiaria;
import com.algoworks.algafood.domain.service.VendaQueryService;
import com.algoworks.algafood.domain.service.VendaReportService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType; //IMPORT CORRRETO.
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;

	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}

	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultaVendasDiariasPdf(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		
		// PARA OBRIGAR A FAZER O DOWNLOAD DO PDF.
		var headers = new HttpHeaders();	
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
			   .contentType(MediaType.APPLICATION_PDF)
			   .headers(headers)
			   .body(bytesPdf);
	}


}
