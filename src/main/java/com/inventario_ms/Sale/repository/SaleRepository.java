package com.inventario_ms.Sale.repository;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericRepository;
import com.inventario_ms.Sale.domain.Sale;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends MarketDependentGenericRepository<Sale, Long> {
}
