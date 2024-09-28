package com.inventario_ms.Supplier.repository;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Supplier.domain.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends MarketDependentGenericRepository<Supplier,Long> {

    @Query("SELECT count(s)>0 FROM Supplier s INNER JOIN s.products mps " +
            "WHERE mps.supplier.id = :supplierId AND mps.fechaDesasignacion IS NULL")
    boolean existsActiveBySupplierId(Long supplierId);
}
