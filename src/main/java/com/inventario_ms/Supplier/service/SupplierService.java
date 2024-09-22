package com.inventario_ms.Supplier.service;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.service.MarketService;
import com.inventario_ms.Product.domain.Product;
import com.inventario_ms.Product.service.ProductService;
import com.inventario_ms.Supplier.dto.SupplierDTO;
import com.inventario_ms.Supplier.domain.Supplier;
import com.inventario_ms.Supplier.domain.SupplierProduct;
import com.inventario_ms.Supplier.repository.SupplierProductRepository;
import com.inventario_ms.Supplier.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SupplierService extends MarketDependentGenericService<Supplier, SupplierDTO, SupplierRepository, Long> {
    private final SupplierRepository supplierRepository;
    private final ProductService productService;
    private final SupplierProductRepository supplierProductRepository;
    private final MarketService marketService;

    public SupplierService(SupplierRepository supplierRepository,
                           ProductService productService,
                           SupplierProductRepository supplierProductRepository, MarketService marketService) {
        super(supplierRepository);
        this.supplierRepository = supplierRepository;
        this.productService = productService;

        this.supplierProductRepository = supplierProductRepository;
        this.marketService = marketService;
    }

    public Boolean addProduct(Long supplierId, Long productId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow();
        Product product = productService.findById(productId).orElseThrow();

        boolean isAdded = supplierProductRepository.existsActiveSupplierProduct(supplierId, productId);
        if (isAdded) {
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

    public Boolean deleteProduct(Long supplierId, Long productId) {
        boolean isAdded = supplierProductRepository.existsActiveSupplierProduct(supplierId, productId);
        if (isAdded) {
            Optional<SupplierProduct> optional =
                    supplierProductRepository.findBySupplierIdAndProductId(supplierId, productId);
            if (optional.isPresent()) {
                SupplierProduct supplierProduct = optional.get();
                supplierProduct.setFechaDesasignacion(LocalDateTime.now());
                supplierProductRepository.save(supplierProduct);
                return true;
            }
        }
        return false;
    }

    @Override
    protected Supplier setMarket(Supplier entity, Long marketId) {
        entity.setMarket(marketService.findById(marketId).get());
        return entity;
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
