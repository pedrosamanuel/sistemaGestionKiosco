package com.inventario_ms.Supplier;

import com.inventario_ms.Generic.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierProductRepository extends GenericRepository<SupplierProduct,Long> {
    Optional<List<SupplierProduct>> findBySupplierIdAndProductId(Long providerId, Long productId);
}
