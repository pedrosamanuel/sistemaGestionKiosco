package com.inventario_ms.Product;

import com.inventario_ms.Generic.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController extends GenericController<Product,ProductDTO,Long> {

    public ProductController (ProductService productService){
        super(productService);
    }

}
