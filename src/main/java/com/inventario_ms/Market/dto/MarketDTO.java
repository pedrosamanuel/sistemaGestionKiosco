package com.inventario_ms.Market.dto;

import com.inventario_ms.Generic.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MarketDTO extends BaseDTO<Long> {
    private String name;
}
