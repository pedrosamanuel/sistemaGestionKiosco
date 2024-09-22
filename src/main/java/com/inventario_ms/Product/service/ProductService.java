package com.inventario_ms.Product.service;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericRepository;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericService;
import com.inventario_ms.Product.domain.Product;
import com.inventario_ms.Product.dto.ProductDTO;
import com.inventario_ms.Product.repository.ProductRepository;
import com.inventario_ms.Supplier.domain.Supplier;
import com.inventario_ms.Supplier.domain.SupplierProduct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService extends NonDependentGenericService<Product, ProductDTO,ProductRepository, Long> {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }


    public List<Product> findBySupplierId(Long supplierId) {
        return productRepository.findBySupplierId(supplierId);
    }


    @Override
    protected Product updateEntity(Product entity, Product updatedEntity) {
        entity.setDescripcion(updatedEntity.getDescripcion());
        entity.setMarca(updatedEntity.getMarca());
        return entity;
    }

    @Override
    protected ProductDTO convertToDTO(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setMarca(entity.getMarca());
        dto.setDescripcion(entity.getDescripcion());
        List<SupplierProduct> supplierProducts = entity.getSupplierProducts();
        List<Supplier> suppliers = new ArrayList<>();
        for (SupplierProduct s : supplierProducts) {
            suppliers.add(s.getSupplier());
        }
        dto.setSuppliers(suppliers);
        return dto;
    }
}
