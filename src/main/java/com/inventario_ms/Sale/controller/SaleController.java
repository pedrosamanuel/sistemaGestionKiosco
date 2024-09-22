package com.inventario_ms.Sale.controller;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericController;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericController;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericService;
import com.inventario_ms.Sale.dto.SaleDTO;
import com.inventario_ms.Sale.domain.Sale;
import com.inventario_ms.Sale.repository.SaleRepository;
import com.inventario_ms.Sale.service.SaleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sale")
public class SaleController extends MarketDependentGenericController<Sale, SaleDTO, SaleService, SaleRepository, Long> {

    public SaleController(SaleService service) {
        super(service);
    }

}
