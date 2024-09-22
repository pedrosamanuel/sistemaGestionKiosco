package com.inventario_ms.Generic.MarketDependent;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface MarketDependentGenericRepository<T, ID> extends JpaRepository<T, ID> {
    

    <T> List<T> findAllByMarketId(Long marketId);

    <T> Optional<T> findByIdAndMarketId(Long marketId, ID id);

    void deleteByIdAndMarketId(Long marketId, ID id);
}