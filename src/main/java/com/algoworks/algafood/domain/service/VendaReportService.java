package com.algoworks.algafood.domain.service;

import com.algoworks.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
