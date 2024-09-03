package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Supplier.Supplier;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class PriceList extends BaseEntity<Long> {
    private LocalDate fechaInicioVigencia;
    private LocalDate fechaFinvigencia;
    @OneToMany(mappedBy = "priceList")
    private List<PriceListProduct> priceListProducts = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
