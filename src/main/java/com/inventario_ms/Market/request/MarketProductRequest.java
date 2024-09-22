package com.inventario_ms.Market.request;

import com.inventario_ms.Category.domain.Category;
import com.inventario_ms.Product.domain.Product;
import lombok.Data;

@Data
public class MarketProductRequest {
    private Product product;
    private double precio;
    private int stockActual;
    private int stockMinimo;
    private Category category;
}
