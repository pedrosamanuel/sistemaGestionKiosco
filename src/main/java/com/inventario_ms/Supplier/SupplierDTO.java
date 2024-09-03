package com.inventario_ms.Supplier;

import com.inventario_ms.Generic.BaseDTO;
import lombok.Data;
@Data
public class SupplierDTO extends BaseDTO<Long> {
    private String nombre;
    private String codProveedor;
}
