package com.inventario_ms.Order;

import com.inventario_ms.Generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends GenericRepository<Order, Long> {
}
