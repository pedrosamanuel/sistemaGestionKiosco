package com.inventario_ms.Order;

import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Supplier.Supplier;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "purchase_order")
public class Order extends BaseEntity<Long> {
    private LocalDateTime fechaOrdenDeCompra;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> products;
}
