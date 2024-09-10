package com.inventario_ms.Product;

import com.inventario_ms.Generic.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends GenericRepository<Product,Long> {

    @Query("SELECT p" +" FROM Product p " +
            "JOIN SupplierProduct sp ON p.id=sp.product.id " +
            "WHERE sp.supplier.id = :supplierId")
    List<Product> findBySupplierId(@Param("supplierId") Long supplierId);

    @Query("SELECT p" +" FROM Product p " +
            "JOIN SupplierProduct sp ON p.id=sp.product.id " +
            "WHERE sp.supplier.id = :supplierId " +
            "AND p.stockActual < p.stockMinimo")
    List<Product> findBySupplierIdAndStock(@Param("supplierId") Long supplierId);

}
