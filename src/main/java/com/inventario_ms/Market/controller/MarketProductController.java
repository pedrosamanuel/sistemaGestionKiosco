package com.inventario_ms.Market.controller;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericController;
import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Market.dto.MarketProductDTO;
import com.inventario_ms.Market.repository.MarketProductRepository;
import com.inventario_ms.Market.service.MarketProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marketProduct")
public class MarketProductController extends MarketDependentGenericController<MarketProduct, MarketProductDTO,
        MarketProductService, MarketProductRepository, Long> {
    private final MarketProductService marketProductService;
    public MarketProductController(MarketProductService marketProductService) {
        super(marketProductService);
        this.marketProductService = marketProductService;
    }

    @GetMapping("/pages")
    public ResponseEntity<PagedModel<MarketProductDTO>> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @CookieValue(value = "marketId", defaultValue = "null") String cookie) {

        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Long marketId = Long.parseLong(cookie);
        Page<MarketProductDTO> marketProductPage = marketProductService.getPaginatedProducts(marketId, page, size);

        PagedModel<MarketProductDTO> pagedModel = new PagedModel<>(marketProductPage);
        return ResponseEntity.ok(pagedModel);
    }
}
