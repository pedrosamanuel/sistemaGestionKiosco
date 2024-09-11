package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListRepository extends GenericRepository<PriceList,Long> {
    @Query("SELECT pl FROM PriceList pl " +
            "WHERE pl.supplier.id = :supplierId " +
            "AND pl.fechaFinVigencia >= CURRENT_DATE " +
            "AND pl.fechaInicioVigencia <= CURRENT_TIMESTAMP " +
            "ORDER BY pl.fechaInicioVigencia DESC")
    List<PriceList> findBySupplierAndDate(@Param("supplierId") Long supplierId);
}
