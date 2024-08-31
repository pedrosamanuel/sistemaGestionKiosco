package com.inventario_ms.Product;

import com.inventario_ms.Generic.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends GenericRepository<Product,Long> {
}
