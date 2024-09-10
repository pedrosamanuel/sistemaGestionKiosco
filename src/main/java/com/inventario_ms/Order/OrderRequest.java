package com.inventario_ms.Order;

import lombok.Data;

@Data
public class OrderRequest {
    private boolean discountProducts;
    private boolean belowMinStock;
    private boolean allProducts;
}
