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
import com.inventario_ms.Supplier.domain.Supplier;
import com.inventario_ms.Supplier.domain.SupplierProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarketService extends NonDependentGenericService<Market, MarketDTO, MarketRepository, Long> {
    private final MarketRepository marketRepository;
    private final ProductService productService;
    private final MarketProductRepository marketProductRepository;
    public MarketService(MarketRepository marketRepository, ProductService productService, MarketProductRepository marketProductRepository) {
        super(marketRepository);
        this.marketRepository = marketRepository;
        this.productService = productService;
        this.marketProductRepository = marketProductRepository;
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

    public Boolean addProduct(Long marketId, MarketProductRequest marketProductRequest) {
        Long productId = marketProductRequest.getProduct().getId();
        Market market = marketRepository.findById(marketId).orElseThrow();
        Product product = productService.findById(productId).orElseThrow();

        boolean isAdded = marketProductRepository.existsByMarketIdAndProductId(marketId,productId);
        if (isAdded) {
            return false;
        }
        MarketProduct marketProduct = MarketProduct.builder()
                .precio(marketProductRequest.getPrecio())
                .stockActual(marketProductRequest.getStockActual())
                .stockMinimo(marketProductRequest.getStockMinimo())
                .product(product)
                .category(marketProductRequest.getCategory())
                .market(market)
                .build();
        marketProductRepository.save(marketProduct);
        return true;
    }

    public Boolean deleteProduct(Long marketId, Long productId) {
        boolean isAdded = marketProductRepository.existsByMarketIdAndProductId(marketId, productId);
        if (isAdded) {
            Optional<MarketProduct> optional =
                    marketProductRepository.findByMarketIdAndProductId(marketId, productId);
            if (optional.isPresent()) {
                MarketProduct marketProduct = optional.get();
                marketProductRepository.deleteById(marketProduct.getId());
                return true;
            }
        }
        return false;
    }
    public MarketProduct findByMarketIdAndProductId(Long marketId, Long productId){
        return marketProductRepository.findByMarketIdAndProductId(marketId,productId).get();
    }

    public List<MarketDTO> findAllByUserId(Long userId) {
        List<Market> markets = marketRepository.findAllByUserId(userId);
        return markets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Page<MarketProduct> getPaginatedProducts(Long marketId, int page, int size) {
        return marketProductRepository.findAllByMarketId(marketId, PageRequest.of(page, size));
    }
}
