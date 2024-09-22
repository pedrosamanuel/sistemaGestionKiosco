package com.inventario_ms.Sale.dto;

import com.inventario_ms.Generic.BaseDTO;
import com.inventario_ms.Sale.domain.SaleMarketProduct;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class SaleDTO extends BaseDTO<Long> {
    private LocalDateTime dateSale;
    private double totalSale;
    private List<SaleMarketProduct> saleMarketProducts = new ArrayList<>();
}
