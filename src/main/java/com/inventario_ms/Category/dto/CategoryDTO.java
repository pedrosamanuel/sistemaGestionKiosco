package com.inventario_ms.Category.dto;

import com.inventario_ms.Generic.BaseDTO;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Product.domain.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryDTO extends BaseDTO<Long> {
    private String nombre;
    private double costoMultiplicador;
}
