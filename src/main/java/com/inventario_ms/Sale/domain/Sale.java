package com.inventario_ms.Sale.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Market.domain.Market;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
public class Sale extends BaseEntity<Long> {
    private LocalDateTime dateSale;
    private double totalSale;
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<SaleMarketProduct> saleMarketProducts = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "market_id")
    @JsonIgnore
    Market market;

    public Sale(double totalSale, List<SaleMarketProduct> saleMarketProducts, Market market) {
        this.dateSale = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        this.totalSale = totalSale;
        this.saleMarketProducts = saleMarketProducts;
        this.market = market;
    }

    public Sale() {
        this.dateSale = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
    }
}

