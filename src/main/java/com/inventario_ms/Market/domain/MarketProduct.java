package com.inventario_ms.Market.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Category.domain.Category;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Product.domain.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketProduct extends BaseEntity<Long> {
    private double precio;
    private int stockActual;
    private int stockMinimo;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "market_id")
    @JsonIgnore
    private Market market;
    @OneToMany(mappedBy = "marketProduct")
    @Builder.Default
    private List<MarketProductSupplier> suppliers = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
