package com.inventario_ms.Supplier.dto;

import com.inventario_ms.Generic.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierDTO extends BaseDTO<Long> {
    private String nombre;
}
