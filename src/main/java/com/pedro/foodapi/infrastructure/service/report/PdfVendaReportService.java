package com.pedro.foodapi.infrastructure.service.report;

import com.pedro.foodapi.domain.filter.VendaDiariaFilter;
import com.pedro.foodapi.domain.service.VendaQueryService;
import com.pedro.foodapi.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfVendaReportService implements VendaReportService {

    @Autowired
    public VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset) {
        try {

            var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            var paramentros = new HashMap<String, Object>();
            paramentros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var vendasDiarias = vendaQueryService.consultarVendasDiarias(filter, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            var jasperPrint = JasperFillManager.fillReport(inputStream, paramentros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }catch (Exception e){
            throw new ReportException("nao foi possivel emitir relatorio de vendas diarias", e);
        }
    }
}
