package com.inventario_ms.Sale.event;

import com.inventario_ms.Sale.domain.SaleMarketProduct;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class SaleEvent extends ApplicationEvent {
    private final List<SaleMarketProduct> saleMarketProducts;

    public SaleEvent(Object source, List<SaleMarketProduct> saleMarketProducts) {
        super(source);
        this.saleMarketProducts = saleMarketProducts;
    }
}
