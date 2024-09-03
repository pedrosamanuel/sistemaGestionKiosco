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

    public Boolean addProduct(Long supplierId, Long productId, String codExternoProducto){
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        SupplierProduct supplierProduct =
                new SupplierProduct(codExternoProducto,
                        LocalDateTime.now(),
                        null,
                        supplier,
                        product);
        Optional<List<SupplierProduct>> optionalSupplierProduct = supplierProductRepository.findBySupplierIdAndProductId(supplierId,productId);
        if (optionalSupplierProduct.isPresent()){
            List<SupplierProduct> supplierProducts = optionalSupplierProduct.get();
            for (SupplierProduct p : supplierProducts){
                if (p.getFechaDesasignacion() == null){
                    return false;
                }
            }
        }
        supplierProductRepository.save(supplierProduct);
        return true;
    }
    public Boolean deleteProduct(Long supplierId, Long productId){
        Optional<List<SupplierProduct>> optionalSupplierProduct = supplierProductRepository.findBySupplierIdAndProductId(supplierId,productId);
        if (optionalSupplierProduct.isPresent()){
            List<SupplierProduct> supplierProducts = optionalSupplierProduct.get();
            for (SupplierProduct p : supplierProducts){
                if (p.getFechaDesasignacion() == null){
                    p.setFechaDesasignacion(LocalDateTime.now());
                    supplierProductRepository.save(p);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected Supplier updateEntity(Supplier entity, Supplier updatedEntity) {
        entity.setCodProveedor(updatedEntity.getCodProveedor());
        entity.setNombre(updatedEntity.getNombre());
        return entity;
    }

    @Override
    protected SupplierDTO convertToDTO(Supplier entity) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(entity.getId());
        dto.setCodProveedor(entity.getCodProveedor());
        dto.setNombre(entity.getNombre());
        return dto;
    }
}
