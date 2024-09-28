package com.inventario_ms.Market.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Generic.BaseDTO;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Supplier.domain.Supplier;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketProductSupplier extends BaseEntity<Long> {
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaDesasignacion;
    @JoinColumn(name = "supplier_id")
    @ManyToOne
    private Supplier supplier;
    @JsonIgnore
    @JoinColumn(name = "market_product_id")
    @ManyToOne
    private MarketProduct marketProduct;
}
