package com.inventario_ms.Order;

import com.inventario_ms.Generic.GenericRepository;
import com.inventario_ms.Generic.GenericService;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends GenericService<Order, OrderDTO, Long> {

    public OrderService(OrderRepository orderRepository) {
        super(orderRepository);
    }

    @Override
    protected Order updateEntity(Order entity, Order updatedEntity) {
        entity.setFechaOrdenDeCompra(updatedEntity.getFechaOrdenDeCompra());
        entity.setProducts(updatedEntity.getProducts());
        entity.setSupplier(updatedEntity.getSupplier());
        return entity;
    }

    @Override
    protected OrderDTO convertToDTO(Order entity) {
        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setFechaOrdenDeCompra(entity.getFechaOrdenDeCompra());
        dto.setProducts(entity.getProducts());
        dto.setSupplier(entity.getSupplier());
        return dto;
    }
}
