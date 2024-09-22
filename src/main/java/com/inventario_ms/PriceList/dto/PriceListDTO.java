package com.inventario_ms.PriceList.dto;

import com.inventario_ms.PriceList.domain.PriceListProduct;
import com.inventario_ms.Supplier.domain.Supplier;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class PriceListDTO  {
    private LocalDateTime fechaInicioVigencia;
    private LocalDate fechaFinVigencia;
    private List<PriceListProduct> priceListProducts = new ArrayList<>();
    private Supplier supplier;
}
