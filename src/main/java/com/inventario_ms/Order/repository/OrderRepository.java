package com.inventario_ms.Order.repository;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericRepository;
import com.inventario_ms.Order.domain.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends NonDependentGenericRepository<Order, Long> {
}
