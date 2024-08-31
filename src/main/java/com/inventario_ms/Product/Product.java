package com.inventario_ms.Product;

import com.inventario_ms.Generic.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Product extends BaseEntity<Long> {
    private String marca;
    private String nombre;
    private String descripcion;
    private int stockActual;
    private int stockMinimo;
    private double precio;
}
