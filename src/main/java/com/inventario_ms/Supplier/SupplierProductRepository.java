package com.inventario_ms.Supplier;

import com.inventario_ms.Generic.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierProductRepository extends GenericRepository<SupplierProduct,Long> {
    @Query(value = "SELECT count(sp) > 0 FROM supplier_product sp " +
            "WHERE sp.supplier_id = :supplierId and sp.product_id = :productId and sp.fecha_desasignacion IS NULL", nativeQuery = true )
    boolean existsActiveSupplierProduct(@Param("supplierId")Long supplierId,@Param("productId")Long productId);

    Optional<SupplierProduct> findBySupplierIdAndProductId(Long supplierId, Long productId);
}
