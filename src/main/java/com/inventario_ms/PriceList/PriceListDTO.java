package com.inventario_ms.PriceList;

import com.inventario_ms.Supplier.Supplier;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
public class PriceListDTO  {
    private LocalDate fechaInicioVigencia;
    private LocalDate fechaFinvigencia;
    private List<PriceListProduct> priceListProducts = new ArrayList<>();
    private Supplier supplier;
}
