package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.filter.VendaDiariaFilter;
import net.sf.jasperreports.engine.JRException;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset) throws JRException;

}
