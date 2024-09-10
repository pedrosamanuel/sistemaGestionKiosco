package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListRepository extends GenericRepository<PriceList,Long> {
    @Query("SELECT pl FROM PriceList pl " +
            "WHERE pl.supplier.id = :supplierId " +
            "AND pl.fechaFinVigencia >= CURRENT_DATE " +
            "AND pl.fechaInicioVigencia <= CURRENT_DATE " +
            "ORDER BY pl.fechaInicioVigencia DESC")
    PriceList findBySupplierAndDate(@Param("supplierId") Long supplierId);
}
