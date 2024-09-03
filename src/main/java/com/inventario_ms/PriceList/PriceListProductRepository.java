package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListProductRepository extends GenericRepository<PriceListProduct,Long> {
}
