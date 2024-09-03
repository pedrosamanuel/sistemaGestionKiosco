package com.inventario_ms.Supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Generic.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Supplier extends BaseEntity<Long> {
    private String nombre;
    private String codProveedor;
    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    private List<SupplierProduct> products = new ArrayList<>();
}
