package com.inventario_ms.Supplier.controller;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericController;
import com.inventario_ms.Supplier.dto.SupplierDTO;
import com.inventario_ms.Supplier.repository.SupplierRepository;
import com.inventario_ms.Supplier.service.SupplierService;
import com.inventario_ms.Supplier.domain.Supplier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController extends MarketDependentGenericController<Supplier, SupplierDTO,
        SupplierService, SupplierRepository,Long> {

    public SupplierController(SupplierService supplierService) {
        super(supplierService);
    }
}
