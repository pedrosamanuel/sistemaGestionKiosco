package com.inventario_ms.Supplier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.domain.MarketProductSupplier;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Supplier extends BaseEntity<Long> {
    private String nombre;
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MarketProductSupplier> products = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "market_id")
    @JsonIgnore
    Market market;
}
