package com.inventario_ms.Generic.MarketDependent;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface MarketDependentGenericRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findAllByMarketId(Long marketId);

    Optional<T> findByIdAndMarketId(ID id, Long marketId);

    void deleteByIdAndMarketId(ID id, Long marketId);

}