package com.inventario_ms.Category.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Product.domain.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Category extends BaseEntity<Long> {
    private String nombre;
    private double costoMultiplicador;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<MarketProduct> products = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "market_id")
    @JsonIgnore
    Market market;
}
