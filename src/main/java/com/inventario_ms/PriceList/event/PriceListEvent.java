package com.inventario_ms.PriceList.event;

import com.inventario_ms.PriceList.domain.PriceListProduct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class PriceListEvent extends ApplicationEvent {
    private List<PriceListProduct> priceListProducts;
    private Long marketId;

    public PriceListEvent(Object source, List<PriceListProduct> priceListProducts, Long marketId) {
        super(source);
        this.priceListProducts = priceListProducts;
        this.marketId = marketId;
    }
}
