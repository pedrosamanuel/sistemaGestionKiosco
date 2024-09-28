package com.inventario_ms.Market.service;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Market.dto.MarketProductDTO;
import com.inventario_ms.Market.repository.MarketProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketProductService extends MarketDependentGenericService<MarketProduct, MarketProductDTO, MarketProductRepository, Long> {
    private final MarketService marketService;
    private final MarketProductRepository marketProductRepository;
    public MarketProductService(MarketProductRepository repository, MarketService marketService, MarketProductRepository marketProductRepository) {
        super(repository);
        this.marketService = marketService;
        this.marketProductRepository = marketProductRepository;
    }

    @Override
    protected MarketProduct setMarket(MarketProduct entity, Long marketId) {
        entity.setMarket(marketService.findById(marketId).get());
        return entity;
    }

    @Override
    protected MarketProduct updateEntity(MarketProduct entity, MarketProduct updatedEntity) {
        entity.setCategory(updatedEntity.getCategory());
        entity.setStockMinimo(updatedEntity.getStockMinimo());
        entity.setStockActual(updatedEntity.getStockActual());
        entity.setPrecio(updatedEntity.getPrecio());
        return entity;
    }

    @Override
    protected MarketProductDTO convertToDTO(MarketProduct entity) {
        MarketProductDTO marketProductDTO = new MarketProductDTO();
        marketProductDTO.setId(entity.getId());
        marketProductDTO.setCategory(entity.getCategory());
        marketProductDTO.setPrecio(entity.getPrecio());
        marketProductDTO.setStockActual(entity.getStockActual());
        marketProductDTO.setStockMinimo(entity.getStockMinimo());
        marketProductDTO.setProduct(entity.getProduct());
        return marketProductDTO;
    }

    public List<MarketProduct> findBySupplierId(Long supplierId) {
        return marketProductRepository.findActiveBySupplier(supplierId);
    }
    public MarketProduct findByMarketIdAndProductId(Long marketId, Long productId){
        return marketProductRepository.findByMarketIdAndProductId(marketId,productId).get();
    }

    public Page<MarketProduct> getPaginatedProducts(Long marketId, int page, int size) {
        return marketProductRepository.findAllByMarketId(marketId, PageRequest.of(page, size));
    }
}
