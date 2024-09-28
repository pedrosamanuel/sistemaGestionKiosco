package com.inventario_ms.Product.controller;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericController;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericController;
import com.inventario_ms.Product.domain.Product;
import com.inventario_ms.Product.dto.ProductDTO;
import com.inventario_ms.Product.repository.ProductRepository;
import com.inventario_ms.Product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController extends NonDependentGenericController<Product, ProductDTO,
        ProductService, ProductRepository,Long> {
    private final ProductService productService;
    public ProductController (ProductService productService){
        super(productService);
        this.productService = productService;
    }

    @GetMapping("/cod/{codigo}")
    public ResponseEntity<Product> getByCodigo(@PathVariable String codigo) {
        Optional<Product> product = productService.findByCodigo(codigo);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadProducts(@RequestParam("file") MultipartFile file){
        boolean added = productService.uploadProducts(file);

        if (added) {
            return ResponseEntity.ok().body("Productos agregados correctamente");
        }
        return ResponseEntity.badRequest().build();
    }
}
