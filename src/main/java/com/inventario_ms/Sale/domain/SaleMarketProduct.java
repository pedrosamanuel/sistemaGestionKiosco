package com.inventario_ms.Sale.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Sale.domain.Sale;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SaleMarketProduct extends BaseEntity<Long> {
    private int cantidad;
    private double subtotal;
    @ManyToOne
    @JoinColumn(name = "market_product_id")
    private MarketProduct marketProduct;
    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonIgnore
    private Sale sale;
}
