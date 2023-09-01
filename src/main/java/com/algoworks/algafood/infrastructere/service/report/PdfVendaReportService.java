package com.algoworks.algafood.infrastructere.service.report;

import org.springframework.stereotype.Service;

import com.algoworks.algafood.domain.filter.VendaDiariaFilter;
import com.algoworks.algafood.domain.service.VendaReportService;

@Service
public class PdfVendaReportService implements VendaReportService {

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		
		return null;
	}

}
