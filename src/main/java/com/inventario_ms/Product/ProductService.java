package com.inventario_ms.Product;

import com.inventario_ms.Generic.GenericRepository;
import com.inventario_ms.Generic.GenericService;
import com.inventario_ms.Supplier.Supplier;
import com.inventario_ms.Supplier.SupplierProduct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService extends GenericService<Product,ProductDTO,Long> {
    private final ProductRepository productRepository;
    public ProductService(GenericRepository<Product, Long> repository, ProductRepository productRepository) {
        super(repository);
        this.productRepository = productRepository;
    }

    public List<Product> findBySupplierId(Long supplierId) {
        return productRepository.findBySupplierId(supplierId);
    }


    @Override
    protected Product updateEntity(Product entity, Product updatedEntity) {
        entity.setDescripcion(updatedEntity.getDescripcion());
        entity.setMarca(updatedEntity.getMarca());
        entity.setStockActual(updatedEntity.getStockActual());
        entity.setStockMinimo(updatedEntity.getStockMinimo());
        entity.setPrecio(updatedEntity.getPrecio());
        return entity;
    }

    @Override
    protected ProductDTO convertToDTO(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setMarca(entity.getMarca());
        dto.setDescripcion(entity.getDescripcion());
        dto.setStockActual(entity.getStockActual());
        dto.setStockMinimo(entity.getStockMinimo());
        dto.setPrecio(entity.getPrecio());
        List<SupplierProduct> supplierProducts = entity.getSupplierProducts();
        List<Supplier> suppliers = new ArrayList<>();
        for (SupplierProduct s : supplierProducts) {
            suppliers.add(s.getSupplier());
        }
        dto.setSuppliers(suppliers);
        return dto;
    }


    public List<Product> findBySupplierIdAndStock(Long supplierId) {
        return productRepository.findBySupplierIdAndStock(supplierId);
    }


}
