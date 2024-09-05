package com.inventario_ms.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.PriceList.PriceListProduct;
import com.inventario_ms.Supplier.SupplierProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Product extends BaseEntity<Long> {
    private String marca;
    private String descripcion;
    private int stockActual;
    private int stockMinimo;
    private double precio;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<SupplierProduct> supplierProducts = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<PriceListProduct> priceListProducts= new ArrayList<>();
}
