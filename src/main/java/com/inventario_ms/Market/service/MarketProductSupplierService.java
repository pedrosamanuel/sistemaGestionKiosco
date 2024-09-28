package com.inventario_ms.Market.service;

import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Market.domain.MarketProductSupplier;
import com.inventario_ms.Market.repository.MarketProductSupplierRepository;
import com.inventario_ms.Supplier.domain.Supplier;
import com.inventario_ms.Supplier.service.SupplierService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarketProductSupplierService {
    private final MarketProductSupplierRepository marketProductSupplierRepository;
    private final MarketProductService marketProductService;
    private final SupplierService supplierService;

    public MarketProductSupplierService(MarketProductSupplierRepository marketProductSupplierRepository, MarketProductService marketProductService, SupplierService supplierService) {
        this.marketProductSupplierRepository = marketProductSupplierRepository;
        this.marketProductService = marketProductService;
        this.supplierService = supplierService;
    }

    public List<MarketProductSupplier> findSuppliers(Long marketProductId) {
        return marketProductSupplierRepository.findActiveSuppliers(marketProductId);
    }

    public Boolean addSupplier(Long marketProductId, Long supplierId) {
        boolean isAdded = marketProductSupplierRepository.existsActiveMarketProductSupplier(marketProductId, supplierId);
        if (isAdded) {
            return false;
        }
        Supplier supplier = supplierService.findById(supplierId).orElseThrow();
        MarketProduct marketProduct = marketProductService.findById(marketProductId).orElseThrow();
        MarketProductSupplier marketProductSupplier =
                new MarketProductSupplier(
                        LocalDateTime.now(),
                        null,
                        supplier,
                        marketProduct);
        marketProductSupplierRepository.save(marketProductSupplier);
        return true;
    }

    public Boolean deleteSupplier(Long marketProductId, Long supplierId) {
        boolean isAdded = marketProductSupplierRepository.existsActiveMarketProductSupplier(marketProductId, supplierId);
        if (!isAdded) {
            return false;
        }
        MarketProductSupplier marketProductSupplier = marketProductSupplierRepository.findByMarketProductIdAndSupplierId(marketProductId,supplierId);
        marketProductSupplier.setFechaDesasignacion(LocalDateTime.now());
        marketProductSupplierRepository.save(marketProductSupplier);
        return true;
    }

    public boolean existsBySupplierId(Long supplierId) {
        return marketProductSupplierRepository.existsActiveBySupplier(supplierId);
    }
}

