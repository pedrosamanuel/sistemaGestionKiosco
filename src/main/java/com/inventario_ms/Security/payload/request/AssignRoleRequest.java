package com.inventario_ms.Security.payload.request;

import com.inventario_ms.Security.model.ERole;
import lombok.Data;

@Data
public class AssignRoleRequest {
    private String username;
    private String roleName;
}
