package com.inventario_ms.Product.repository;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericRepository;
import com.inventario_ms.Product.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends NonDependentGenericRepository<Product,Long> {

    Optional<Product> findByCodigo(String codigo);

    @Query("SELECT DISTINCT p.codigo FROM Product p")
    Set<String> findAllCodigos();
}
