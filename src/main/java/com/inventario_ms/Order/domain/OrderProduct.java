package com.inventario_ms.Order.domain;

import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Order.domain.Order;
import com.inventario_ms.Product.domain.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
public class OrderProduct extends BaseEntity<Long> {
    private int cantidad;
    private double precioUnitario;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
