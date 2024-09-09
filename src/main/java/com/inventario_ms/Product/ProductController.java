package com.inventario_ms.Product;

import com.inventario_ms.Generic.GenericController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController extends GenericController<Product,ProductDTO,Long> {

    public ProductController (ProductService productService){
        super(productService);
    }

}
