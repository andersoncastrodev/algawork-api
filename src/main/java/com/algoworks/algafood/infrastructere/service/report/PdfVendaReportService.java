package com.algoworks.algafood.infrastructere.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algoworks.algafood.domain.filter.VendaDiariaFilter;
import com.algoworks.algafood.domain.service.VendaQueryService;
import com.algoworks.algafood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportService implements VendaReportService {

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		
		try {
		//Pegar o arquivo do relatorio.
		var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");
		
		//Localizacao
		var parametros = new HashMap<String , Object>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		//Fonte dos Dados.
		var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
		
		var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
		
		var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
		} catch (JRException e) {
			throw new ReportException("Não foi possivel emitir relatorio de vendas diárias",e);
		}
		
	}

}
