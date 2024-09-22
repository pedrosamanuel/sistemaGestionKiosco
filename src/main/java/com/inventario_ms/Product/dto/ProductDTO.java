package com.inventario_ms.Product.dto;

import com.inventario_ms.Category.domain.Category;
import com.inventario_ms.Generic.BaseDTO;
import com.inventario_ms.Supplier.domain.Supplier;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDTO extends BaseDTO<Long> {
    private String marca;
    private String descripcion;
    private int stockActual;
    private int stockMinimo;
    private double precio;
    private List<Supplier> suppliers = new ArrayList<>();
    private Category category;
}
