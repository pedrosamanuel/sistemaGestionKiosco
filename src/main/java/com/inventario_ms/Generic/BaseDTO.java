package com.inventario_ms.Generic;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseDTO<T> {

    private T id;

    public BaseDTO() {
    }

    public BaseDTO(T id) {
        this.id = id;
    }

}
