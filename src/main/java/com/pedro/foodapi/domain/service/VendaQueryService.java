package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.filter.VendaDiariaFilter;
import com.pedro.foodapi.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
