package com.inventario_ms.Order;

import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aspectj.weaver.ast.Or;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
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
