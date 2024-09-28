package com.inventario_ms.Supplier.service;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Market.repository.MarketProductSupplierRepository;
import com.inventario_ms.Market.service.MarketProductSupplierService;
import com.inventario_ms.Market.service.MarketService;
import com.inventario_ms.Product.service.ProductService;
import com.inventario_ms.Supplier.dto.SupplierDTO;
import com.inventario_ms.Supplier.domain.Supplier;

import com.inventario_ms.Supplier.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierService extends MarketDependentGenericService<Supplier, SupplierDTO, SupplierRepository, Long> {
    private final SupplierRepository supplierRepository;
    private final MarketService marketService;

    public SupplierService(SupplierRepository supplierRepository,
                           MarketService marketService) {
        super(supplierRepository);
        this.supplierRepository = supplierRepository;
        this.marketService = marketService;
    }

    @Override
    @Transactional
    public Boolean deleteById(Long marketId, Long supplierId) {
        if (supplierRepository.existsActiveBySupplierId(supplierId)) {
            throw new IllegalStateException("No se puede eliminar el proveedor porque hay productos asociados.");
        }
        Optional<Supplier> existingEntity = supplierRepository.findByIdAndMarketId(supplierId, marketId);
        if (existingEntity.isPresent()) {
            supplierRepository.deleteByIdAndMarketId(supplierId, marketId);
            return true;
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
