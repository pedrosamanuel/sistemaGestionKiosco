package com.inventario_ms.Market.repository;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Market.domain.MarketProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketProductRepository extends MarketDependentGenericRepository<MarketProduct, Long> {

    boolean existsByMarketIdAndProductId(Long marketId, Long productId);

    Optional<MarketProduct> findByMarketIdAndProductId(Long marketId, Long productId);

    Page<MarketProduct> findAllByMarketId(Long marketId, PageRequest of);
}
