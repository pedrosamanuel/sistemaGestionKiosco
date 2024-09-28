package com.inventario_ms.Market.repository;

import com.inventario_ms.Generic.NonDependent.NonDependentGenericRepository;
import com.inventario_ms.Market.domain.MarketProductSupplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketProductSupplierRepository extends NonDependentGenericRepository<MarketProductSupplier, Long> {
    @Query("SELECT mps FROM MarketProductSupplier mps WHERE mps.marketProduct.id = :marketProductId AND " +
            "mps.fechaDesasignacion IS NULL")
    List<MarketProductSupplier> findActiveSuppliers(@Param("marketProductId") Long marketProductId);

    @Query("SELECT count(mps) > 0 FROM MarketProductSupplier mps WHERE mps.marketProduct.id = :marketProductId " +
            "AND mps.supplier.id = :supplierId AND mps.fechaDesasignacion IS NULL")
    boolean existsActiveMarketProductSupplier(@Param("marketProductId") Long marketProductId, @Param("supplierId") Long supplierId);

    @Query("SELECT mps FROM MarketProductSupplier mps WHERE mps.marketProduct.id = :marketProductId " +
            "AND mps.supplier.id = :supplierId AND mps.fechaDesasignacion IS NULL")
    MarketProductSupplier findByMarketProductIdAndSupplierId(@Param("marketProductId") Long marketProductId, @Param("supplierId") Long supplierId);

    @Query("SELECT count(mps) > 0 FROM MarketProductSupplier mps WHERE mps.supplier.id = :supplierId AND mps.fechaDesasignacion IS NULL")
    boolean existsActiveBySupplier(@Param("supplierId") Long supplierId);
}
