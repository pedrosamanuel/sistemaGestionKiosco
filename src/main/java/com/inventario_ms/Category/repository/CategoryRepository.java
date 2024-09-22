package com.inventario_ms.Category.repository;

import com.inventario_ms.Category.domain.Category;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MarketDependentGenericRepository<Category, Long> {
}
