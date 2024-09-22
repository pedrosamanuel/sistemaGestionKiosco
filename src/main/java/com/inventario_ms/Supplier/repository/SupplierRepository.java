package com.inventario_ms.Supplier.repository;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Supplier.domain.Supplier;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends MarketDependentGenericRepository<Supplier,Long> {
}
