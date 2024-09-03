package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.BaseDTO;
import com.inventario_ms.Supplier.Supplier;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class PriceListDTO extends BaseDTO<Long> {
    private LocalDate fechaInicioVigencia;
    private LocalDate fechaFinvigencia;
    private List<PriceListProduct> priceListProducts = new ArrayList<>();
    private Supplier supplier;
}
