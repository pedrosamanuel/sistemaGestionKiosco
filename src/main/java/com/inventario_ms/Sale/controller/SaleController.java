package com.inventario_ms.Sale.controller;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericController;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericController;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericService;
import com.inventario_ms.Sale.dto.SaleDTO;
import com.inventario_ms.Sale.domain.Sale;
import com.inventario_ms.Sale.repository.SaleRepository;
import com.inventario_ms.Sale.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
public class SaleController extends MarketDependentGenericController<Sale, SaleDTO, SaleService, SaleRepository, Long> {
    private final SaleService saleService;
    public SaleController(SaleService saleService) {
        super(saleService);
        this.saleService = saleService;
    }

    @GetMapping("/today")
    public ResponseEntity<List<Sale>> getTodaySales(@CookieValue(value = "marketId",defaultValue = "null") String cookie) {
        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);
        return ResponseEntity.ok(saleService.findByDate(marketId));
    }
}
