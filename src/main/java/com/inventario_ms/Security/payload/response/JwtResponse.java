package com.inventario_ms.Security.payload.response;

import com.inventario_ms.Security.model.UserMarketRole;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private String email;
    private List<UserMarketRole> roles;

    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email, List<UserMarketRole> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
