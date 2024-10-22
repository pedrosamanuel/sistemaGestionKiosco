package com.inventario_ms.Market.listener;

import com.inventario_ms.Market.service.MarketProductService;
import com.inventario_ms.Order.event.OrderEvent;
import com.inventario_ms.PriceList.domain.PriceList;
import com.inventario_ms.PriceList.domain.PriceListProduct;
import com.inventario_ms.PriceList.event.PriceListEvent;
import com.inventario_ms.Sale.event.SaleEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MarketProductListener {
    private final MarketProductService marketProductService;

    public MarketProductListener(MarketProductService marketProductService) {
        this.marketProductService = marketProductService;
    }

    @EventListener
    public void handleSale(SaleEvent saleEvent){
        marketProductService.reduceStock(saleEvent.getSaleMarketProducts());
    }

    @EventListener
    public void handleOrder(OrderEvent orderEvent){
        marketProductService.increaseStock(orderEvent.getOrderProducts(), orderEvent.getMarketId());
    }

    @EventListener
    public void handlePrices(PriceListEvent priceListEvent){
        marketProductService.changePrice(priceListEvent.getPriceListProducts(),
                priceListEvent.getMarketId());
    }

}
