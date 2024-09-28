package com.inventario_ms.Market.repository;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Market.domain.MarketProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketProductRepository extends MarketDependentGenericRepository<MarketProduct, Long> {

    Optional<MarketProduct> findByMarketIdAndProductId(Long marketId, Long productId);

    Page<MarketProduct> findAllByMarketId(Long marketId, PageRequest of);

    boolean existsByCategoryId(Long categoryId);

    @Query("SELECT mp FROM MarketProduct mp JOIN MarketProductSupplier mps ON " +
            "mps.marketProduct.id = mp.id WHERE mps.supplier.id = :supplierId " +
            "AND mps.fechaDesasignacion IS NULL")
    List<MarketProduct> findActiveBySupplier(@Param("supplierId") Long supplierId);
}
