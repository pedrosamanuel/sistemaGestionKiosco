package com.inventario_ms.Security.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;
}