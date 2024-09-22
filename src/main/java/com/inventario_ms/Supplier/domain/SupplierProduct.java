package com.inventario_ms.Supplier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Product.domain.Product;
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
public class SupplierProduct extends BaseEntity<Long> {
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaDesasignacion;
    @JoinColumn(name = "supplier_id")
    @ManyToOne
    private Supplier supplier;
    @JsonIgnore
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
}
