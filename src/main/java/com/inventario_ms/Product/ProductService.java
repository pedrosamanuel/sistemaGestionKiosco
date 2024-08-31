package com.inventario_ms.Product;

import com.inventario_ms.Generic.GenericRepository;
import com.inventario_ms.Generic.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends GenericService<Product,Long> {
    public ProductService(GenericRepository<Product, Long> repository) {
        super(repository);
    }

    @Override
    protected Product updateEntity(Product entity, Product updatedEntity) {
        entity.setNombre(updatedEntity.getNombre());
        entity.setDescripcion(updatedEntity.getDescripcion());
        entity.setMarca(updatedEntity.getMarca());
        entity.setStockActual(updatedEntity.getStockActual());
        entity.setStockMinimo(updatedEntity.getStockMinimo());
        entity.setPrecio(updatedEntity.getPrecio());
        return entity;
    }


}
