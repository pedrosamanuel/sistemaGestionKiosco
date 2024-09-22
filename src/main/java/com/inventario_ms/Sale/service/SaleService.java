package com.inventario_ms.Sale.service;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericService;
import com.inventario_ms.Market.service.MarketService;
import com.inventario_ms.Sale.domain.Sale;
import com.inventario_ms.Sale.dto.SaleDTO;
import com.inventario_ms.Sale.repository.SaleRepository;
import org.springframework.stereotype.Service;

@Service
public class SaleService extends MarketDependentGenericService<Sale, SaleDTO, SaleRepository, Long> {

    private final MarketService marketService;
    public SaleService(SaleRepository repository, MarketService marketService) {
        super(repository);
        this.marketService = marketService;
    }

    @Override
    protected Sale setMarket(Sale entity, Long marketId) {
        entity.setMarket(marketService.findById(marketId).get());
        return entity;
    }

    @Override
    protected Sale updateEntity(Sale entity, Sale updatedEntity) {
        return null;
    }

    @Override
    protected SaleDTO convertToDTO(Sale entity) {
        SaleDTO dto = new SaleDTO();
        dto.setId(entity.getId());
        dto.setDateSale(entity.getDateSale());
        dto.setSaleMarketProducts(entity.getSaleMarketProducts());
        dto.setTotalSale(entity.getTotalSale());
        return dto;
    }
}
