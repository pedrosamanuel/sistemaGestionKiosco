package com.inventario_ms.Order.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private boolean discountProducts;
    private boolean belowMinStock;
    private boolean allProducts;
}
