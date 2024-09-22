package com.inventario_ms.Market.controller;


import com.inventario_ms.Generic.NonDependent.NonDependentGenericController;
import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Market.dto.MarketDTO;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.repository.MarketRepository;
import com.inventario_ms.Market.request.MarketProductRequest;
import com.inventario_ms.Market.service.MarketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market")
public class MarketController extends NonDependentGenericController<Market, MarketDTO,
        MarketService, MarketRepository, Long> {

    private final MarketService marketService;
    public MarketController(MarketService marketService) {
        super(marketService);
        this.marketService = marketService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MarketDTO>> getAllByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(marketService.findAllByUserId(userId));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> selectMarket(
            @RequestParam Long marketId,
            @PathVariable Long userId,
            HttpSession session) {

        if (!marketService.marketBelongsToUser(marketId, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado");
        }
        session.setAttribute("selectedMarketId", marketId);
        return ResponseEntity.ok("Market seleccionado correctamente");
    }
    @GetMapping("/{marketId}/getProduct")
    public Page<MarketProduct> getPaginatedProducts(
            @PathVariable Long marketId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return marketService.getPaginatedProducts(marketId, page, size);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(
            @RequestBody MarketProductRequest marketProductRequest,
            HttpSession session){
        Long marketId = (Long) session.getAttribute("selectedMarketId");

        Boolean isAdded = marketService.addProduct(marketId , marketProductRequest);
        if(isAdded) {
            return ResponseEntity.ok("Producto añadido correctamente");
        }
        return ResponseEntity.ok("Producto no añadido correctamente");
    }
    @PostMapping("/deleteProduct")
    public ResponseEntity<String> deleteProduct(
            @RequestParam Long productId,
            HttpSession session){
        Long marketId = (Long) session.getAttribute("selectedMarket");
        Boolean isDeleted = marketService.deleteProduct(marketId,productId);
        if (isDeleted) {
            return ResponseEntity.ok("Producto eliminado correctamente");
        }
        return ResponseEntity.ok("Producto no eliminado correctamente");

    }
}
