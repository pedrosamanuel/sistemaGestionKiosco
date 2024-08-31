package com.inventario_ms.Proveedor;

import com.inventario_ms.Generic.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Provider extends BaseEntity<Long> {
    private String nombre;
    private String codProveedor;
}
