package com.inventario_ms.Product.controller;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericController;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericController;
import com.inventario_ms.Product.domain.Product;
import com.inventario_ms.Product.dto.ProductDTO;
import com.inventario_ms.Product.repository.ProductRepository;
import com.inventario_ms.Product.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController extends NonDependentGenericController<Product, ProductDTO,
        ProductService, ProductRepository,Long> {
    public ProductController (ProductService productService){
        super(productService);
    }

}
