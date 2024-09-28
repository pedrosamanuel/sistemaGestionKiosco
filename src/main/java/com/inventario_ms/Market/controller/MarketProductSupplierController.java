package com.inventario_ms.Market.controller;

import com.inventario_ms.Market.domain.MarketProductSupplier;
import com.inventario_ms.Market.service.MarketProductSupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marketProductSupplier")
public class MarketProductSupplierController {
    private final MarketProductSupplierService marketProductSupplierService;

    public MarketProductSupplierController(MarketProductSupplierService marketProductSupplierService) {
        this.marketProductSupplierService = marketProductSupplierService;
    }

    @GetMapping("/{marketProductId}")
    public ResponseEntity<List<MarketProductSupplier>> findSuppliers(@PathVariable Long marketProductId){
        List<MarketProductSupplier> marketProductSuppliers =
                marketProductSupplierService.findSuppliers(marketProductId);

        return ResponseEntity.ok(marketProductSuppliers);
    }

    @PostMapping("/{marketProductId}/addSupplier")
    public ResponseEntity<String> addSupplier(@PathVariable Long marketProductId,
                                             @RequestParam Long supplierId){

        Boolean isAdded = marketProductSupplierService.addSupplier(marketProductId, supplierId);
        if(isAdded) {
            return ResponseEntity.ok("Producto añadido correctamente");
        }
        return ResponseEntity.ok("Producto no añadido correctamente");
    }
    @PostMapping("/{marketProductId}/deleteSupplier")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long marketProductId,
                                                @RequestParam Long supplierId){

        Boolean isDeleted = marketProductSupplierService.deleteSupplier(marketProductId, supplierId);
        if (isDeleted) {
            return ResponseEntity.ok("Producto eliminado correctamente");
        }
        return ResponseEntity.ok("Producto no eliminado correctamente");
    }
}
