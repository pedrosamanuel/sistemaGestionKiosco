package com.inventario_ms.Supplier.controller;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericController;
import com.inventario_ms.Supplier.dto.SupplierDTO;
import com.inventario_ms.Supplier.repository.SupplierRepository;
import com.inventario_ms.Supplier.service.SupplierService;
import com.inventario_ms.Supplier.domain.Supplier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController extends MarketDependentGenericController<Supplier, SupplierDTO,
        SupplierService, SupplierRepository,Long> {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        super(supplierService);
        this.supplierService = supplierService;
    }
    @PostMapping("/{supplierId}/addProduct")
    public ResponseEntity<String> addProduct(@PathVariable Long supplierId,
                                             @RequestParam Long productId){

        Boolean isAdded = supplierService.addProduct(supplierId ,productId);
        if(isAdded) {
            return ResponseEntity.ok("Producto añadido correctamente");
        }
        return ResponseEntity.ok("Producto no añadido correctamente");
    }
    @PostMapping("/{supplierId}/deleteProduct")
    public ResponseEntity<String> deleteProduct(@PathVariable Long supplierId,
                                             @RequestParam Long productId){

        Boolean isDeleted = supplierService.deleteProduct(supplierId,productId);
        if (isDeleted) {
            return ResponseEntity.ok("Producto eliminado correctamente");
        }
        return ResponseEntity.ok("Producto no eliminado correctamente");

    }
}
