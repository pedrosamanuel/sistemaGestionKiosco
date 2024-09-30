package com.inventario_ms.Market.listener;

import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Market.service.MarketProductService;
import com.inventario_ms.Order.event.OrderEvent;
import com.inventario_ms.Order.service.OrderExcelService;
import com.inventario_ms.Sale.domain.Sale;
import com.inventario_ms.Sale.event.SaleEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StockListener {
    private final MarketProductService marketProductService;

    public StockListener(MarketProductService marketProductService) {
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

}
