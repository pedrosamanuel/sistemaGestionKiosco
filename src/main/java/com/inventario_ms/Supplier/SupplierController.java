package com.inventario_ms.Supplier;

import com.inventario_ms.Generic.GenericController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController extends GenericController<Supplier,SupplierDTO,Long> {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        super(supplierService);
        this.supplierService = supplierService;
    }
    @PostMapping("/{supplierId}/addProduct")
    public ResponseEntity<String> addProduct(@PathVariable Long supplierId,
                                             @RequestParam Long productId,
                                             @RequestParam String codExternoProduct){

        Boolean isAdded = supplierService.addProduct(supplierId ,productId, codExternoProduct);
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
