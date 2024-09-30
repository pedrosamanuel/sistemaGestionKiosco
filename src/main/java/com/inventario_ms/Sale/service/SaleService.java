package com.inventario_ms.Sale.service;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericService;
import com.inventario_ms.Market.service.MarketService;
import com.inventario_ms.Sale.domain.Sale;
import com.inventario_ms.Sale.dto.SaleDTO;
import com.inventario_ms.Sale.event.SaleEvent;
import com.inventario_ms.Sale.repository.SaleRepository;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SaleService extends MarketDependentGenericService<Sale, SaleDTO, SaleRepository, Long> {
    private final SaleRepository saleRepository;
    private final MarketService marketService;
    private final ApplicationEventPublisher applicationEventPublisher;
    public SaleService(SaleRepository saleRepository, MarketService marketService, ApplicationEventPublisher applicationEventPublisher) {
        super(saleRepository);
        this.saleRepository = saleRepository;
        this.marketService = marketService;
        this.applicationEventPublisher = applicationEventPublisher;
    }
    @Override
    public void save(Long marketId,  Sale sale) {
        applicationEventPublisher.publishEvent(new SaleEvent(this, sale.getSaleMarketProducts()));
        saleRepository.save(setMarket(sale, marketId));
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
