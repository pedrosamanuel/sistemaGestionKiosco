package com.inventario_ms.Market.service;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Market.dto.MarketProductDTO;
import com.inventario_ms.Market.repository.MarketProductRepository;
import com.inventario_ms.Order.domain.OrderProduct;
import com.inventario_ms.PriceList.domain.PriceListProduct;
import com.inventario_ms.Sale.domain.SaleMarketProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarketProductService extends MarketDependentGenericService<MarketProduct, MarketProductDTO, MarketProductRepository, Long> {
    private final MarketService marketService;
    private final MarketProductRepository marketProductRepository;
    public MarketProductService(MarketProductRepository repository, MarketService marketService, MarketProductRepository marketProductRepository) {
        super(repository);
        this.marketService = marketService;
        this.marketProductRepository = marketProductRepository;
    }
    @Transactional
    public void reduceStock(List<SaleMarketProduct> saleMarketProducts) {
        for (SaleMarketProduct smp : saleMarketProducts){
            Optional<MarketProduct> optionalMarketProduct = marketProductRepository.findById(smp.getMarketProduct().getId());
            if (optionalMarketProduct.isPresent()) {
                MarketProduct marketProduct = optionalMarketProduct.get();
                marketProduct.setStockActual(marketProduct.getStockActual() -
                        smp.getCantidad());
            }
        }
    }
    @Transactional
    public void increaseStock(List<OrderProduct> orderProducts, Long marketId){
        for (OrderProduct orderProduct : orderProducts){
            Optional<MarketProduct> optionalMarketProduct =
                    marketProductRepository.findByMarketIdAndProductId(marketId,orderProduct.getProduct().getId());
            if (optionalMarketProduct.isPresent()){
                MarketProduct marketProduct = optionalMarketProduct.get();
                marketProduct.setStockActual(marketProduct.getStockActual() +
                        orderProduct.getCantidad());
            }
        }
    }
    @Transactional
    public void changePrice(List<PriceListProduct> priceListProducts, Long marketId) {
        for (PriceListProduct plp : priceListProducts){
            Optional<MarketProduct> optionalMarketProduct =
                    marketProductRepository.findByMarketIdAndProductId(marketId, plp.getProduct().getId());
            if(optionalMarketProduct.isPresent()){
                MarketProduct marketProduct = optionalMarketProduct.get();
                double multiplier = marketProduct.getCategory().getCostoMultiplicador();
                marketProduct.setPrecio(priceRounder(multiplier, plp.getPrecio()));
            }
        }
    }
    public static double priceRounder(double multiplier, double cost){
        double price = multiplier * cost;

        if (price % 10 >= 5){
            price = Math.ceil(price / 10) * 10;
        }else {
            price = Math.floor(price / 10) * 10;
        }
        return price;
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

    public Page<MarketProductDTO> getPaginatedProducts(Long marketId, int page, int size) {
        Page<MarketProduct> marketProductPage =
                marketProductRepository.findAllByMarketId(marketId, PageRequest.of(page, size));
        return marketProductPage.map(this::convertToDTO);
    }

    public MarketProductDTO findByMarketIdAndCodPro(Long marketId, String codProduct) {
        Optional<MarketProduct> optionalMarketProduct =
                marketProductRepository.findByMarketIdAndCodPro(marketId, codProduct);
        if (optionalMarketProduct.isPresent()){
            MarketProduct marketProduct = optionalMarketProduct.get();
            return convertToDTO(marketProduct);
        }
        return null;
    }

    public MarketProduct addProduct(Long marketId, MarketProduct marketProduct) {
        Optional<MarketProduct> optionalMarketProduct =
                marketProductRepository.findByMarketIdAndProductId(marketId, marketProduct.getProduct().getId());
        if (optionalMarketProduct.isEmpty()){
            Optional<Market> optionalMarket = marketService.findById(marketId);
            marketProduct.setMarket(optionalMarket.orElse(null));
            return marketProductRepository.save(marketProduct);
        }
        return null;
    }
}
