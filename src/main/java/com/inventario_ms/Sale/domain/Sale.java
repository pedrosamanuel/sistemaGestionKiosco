package com.inventario_ms.Sale.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Market.domain.Market;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Sale extends BaseEntity<Long> {
    private LocalDateTime dateSale;
    private double totalSale;
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleMarketProduct> saleMarketProducts = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "market_id")
    @JsonIgnore
    Market market;
}
