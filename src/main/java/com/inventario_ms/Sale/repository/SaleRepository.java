package com.inventario_ms.Sale.repository;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericRepository;
import com.inventario_ms.Sale.domain.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends MarketDependentGenericRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s WHERE s.market.id = :marketId AND DATE(s.dateSale) = :today ORDER BY s.dateSale DESC")
    List<Sale> findByDate(@Param("marketId") Long marketId, @Param("today")LocalDate today);
}
