package com.inventario_ms.Supplier;

import com.inventario_ms.Generic.GenericService;
import com.inventario_ms.Product.Product;
import com.inventario_ms.Product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService extends GenericService<Supplier,SupplierDTO,Long> {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final SupplierProductRepository supplierProductRepository;

    public SupplierService(SupplierRepository supplierRepository,
                           ProductRepository productRepository,
                           SupplierProductRepository supplierProductRepository) {
        super(supplierRepository);
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.supplierProductRepository = supplierProductRepository;
    }

    public Boolean addProduct(Long supplierId, Long productId){
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

       boolean isAdded = supplierProductRepository.existsActiveSupplierProduct(supplierId,productId);
       if (isAdded){
           return false;
       }
       SupplierProduct supplierProduct =
               new SupplierProduct(
                       LocalDateTime.now(),
                       null,
                       supplier,
                       product);
       supplierProductRepository.save(supplierProduct);
       return true;

    }
    public Boolean deleteProduct(Long supplierId, Long productId){
        boolean isAdded = supplierProductRepository.existsActiveSupplierProduct(supplierId,productId);
        if (isAdded){
            Optional<SupplierProduct> optionalsp =
                    supplierProductRepository.findBySupplierIdAndProductId(supplierId,productId);
            if(optionalsp.isPresent()){
                SupplierProduct supplierProduct = optionalsp.get();
                supplierProduct.setFechaDesasignacion(LocalDateTime.now());
                supplierProductRepository.save(supplierProduct);
                return true;
            }
        }
        return false;
    }

    @Override
    protected Supplier updateEntity(Supplier entity, Supplier updatedEntity) {
        entity.setNombre(updatedEntity.getNombre());
        return entity;
    }

    @Override
    protected SupplierDTO convertToDTO(Supplier entity) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }
}
