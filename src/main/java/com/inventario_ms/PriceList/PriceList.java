package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Supplier.Supplier;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class PriceList extends BaseEntity<Long> {
    private LocalDate fechaInicioVigencia;
    private LocalDate fechaFinVigencia;
    @OneToMany(mappedBy = "priceList", cascade = CascadeType.ALL)
    private List<PriceListProduct> priceListProducts = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
