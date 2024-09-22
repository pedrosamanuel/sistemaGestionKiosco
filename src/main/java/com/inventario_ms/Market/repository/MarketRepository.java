package com.inventario_ms.Market.repository;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericRepository;
import com.inventario_ms.Market.domain.Market;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepository extends NonDependentGenericRepository<Market, Long> {
    @Query("SELECT COUNT(m) > 0 " +
            "FROM Market m " +
            "WHERE m.id = :marketId AND m.user.id = :userId")
    boolean marketBelongsToUser(@Param("marketId") Long marketId, @Param("userId") Long userId);

     List<Market> findAllByUserId(Long userId);
}
