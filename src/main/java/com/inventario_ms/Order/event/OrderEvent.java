package com.inventario_ms.Order.event;

import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Order.domain.OrderProduct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;
@Getter
@Setter
public class OrderEvent extends ApplicationEvent {
    private List<OrderProduct> orderProducts;
    private Long marketId;

    public OrderEvent(Object source, List<OrderProduct> orderProducts, Long marketId) {
        super(source);
        this.orderProducts = orderProducts;
        this.marketId = marketId;
    }
}
