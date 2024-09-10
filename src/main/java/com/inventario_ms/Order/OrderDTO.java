package com.inventario_ms.Order;

import com.inventario_ms.Generic.BaseDTO;
import com.inventario_ms.Supplier.Supplier;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDTO extends BaseDTO<Long> {
    private LocalDateTime fechaOrdenDeCompra;
    private Supplier supplier;
    private List<OrderProduct> products;
}

