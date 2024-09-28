package com.inventario_ms.Market.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Category.domain.Category;
import com.inventario_ms.Generic.BaseDTO;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.domain.MarketProductSupplier;
import com.inventario_ms.Product.domain.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class MarketProductDTO extends BaseDTO<Long> {
    private double precio;
    private int stockActual;
    private int stockMinimo;
    private Product product;
    private Category category;
}
