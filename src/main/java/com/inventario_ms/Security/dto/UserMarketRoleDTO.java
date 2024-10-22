package com.inventario_ms.Security.dto;


import com.inventario_ms.Security.model.Role;
import com.inventario_ms.Security.model.User;
import lombok.Data;


@Data
public class UserMarketRoleDTO  {
    private Long id;
    private Long marketId;
    private Role role;
    private User user;

}
