package com.inventario_ms.PriceList.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.PriceList.domain.PriceList;
import com.inventario_ms.Product.domain.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class PriceListProduct extends BaseEntity<Long> {
    private double precio;
    private int cantidad;
    private boolean promocion;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "priceList_id")
    private PriceList priceList;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
