package com.inventario_ms.Market.service;

import com.inventario_ms.Generic.NonDependent.NonDependentGenericService;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Market.dto.MarketDTO;
import com.inventario_ms.Market.repository.MarketProductRepository;
import com.inventario_ms.Market.repository.MarketRepository;
import com.inventario_ms.Market.request.MarketProductRequest;
import com.inventario_ms.Product.domain.Product;
import com.inventario_ms.Product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarketService extends NonDependentGenericService<Market, MarketDTO, MarketRepository, Long> {
    private final MarketRepository marketRepository;
    public MarketService(MarketRepository marketRepository, MarketProductRepository marketProductRepository) {
        super(marketRepository);
        this.marketRepository = marketRepository;
    }


    @Override
    protected Market updateEntity(Market entity, Market updatedEntity) {
        entity.setName(updatedEntity.getName());
        return entity;
    }

    @Override
    protected MarketDTO convertToDTO(Market entity) {
        MarketDTO marketDTO = new MarketDTO();
        marketDTO.setId(entity.getId());
        marketDTO.setName(entity.getName());
        return marketDTO;
    }

    public boolean marketBelongsToUser(Long marketId, Long userId) {
        return marketRepository.marketBelongsToUser(marketId, userId);
    }


    public List<MarketDTO> findAllByUserId(Long userId) {
        List<Market> markets = marketRepository.findAllByUserId(userId);
        return markets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


}
