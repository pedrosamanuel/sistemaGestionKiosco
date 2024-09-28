package com.inventario_ms.Product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Order.domain.OrderProduct;
import com.inventario_ms.PriceList.domain.PriceListProduct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Product extends BaseEntity<Long> {
    @Column(nullable = false, unique = true)
    private String codigo;
    private String descripcion;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<PriceListProduct> priceListProducts= new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<MarketProduct> marketProducts = new ArrayList<>();
}
