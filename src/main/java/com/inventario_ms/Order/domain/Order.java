package com.inventario_ms.Order.domain;

import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Supplier.domain.Supplier;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@Table(name = "purchase_order")
public class Order extends BaseEntity<Long> {
    private LocalDateTime fechaOrdenDeCompra;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderProduct> products = new ArrayList<>();
}
