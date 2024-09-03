package com.inventario_ms.Generic;

public class BaseDTO<T> {

    private T id;

    public BaseDTO() {
    }

    public BaseDTO(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
