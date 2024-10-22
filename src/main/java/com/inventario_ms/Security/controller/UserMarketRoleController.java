package com.inventario_ms.Security.controller;

import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.service.MarketService;
import com.inventario_ms.Security.dto.UserMarketRoleDTO;
import com.inventario_ms.Security.model.ERole;
import com.inventario_ms.Security.model.Role;
import com.inventario_ms.Security.model.User;
import com.inventario_ms.Security.payload.request.AssignRoleRequest;
import com.inventario_ms.Security.payload.request.UnassignRoleRequest;
import com.inventario_ms.Security.service.RoleService;
import com.inventario_ms.Security.service.UserDetailsImpl;
import com.inventario_ms.Security.service.UserDetailsServiceImpl;
import com.inventario_ms.Security.service.UserMarketRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userMarketRole")
public class UserMarketRoleController {
    private final UserMarketRoleService userMarketRoleService;

    private final UserDetailsServiceImpl userDetailsService;

    private final MarketService marketService;

    private final RoleService roleService;

    public UserMarketRoleController(UserMarketRoleService userMarketRoleService,
                                    UserDetailsServiceImpl userDetailsService,
                                    MarketService marketService,
                                    RoleService roleService) {
        this.userMarketRoleService = userMarketRoleService;
        this.userDetailsService = userDetailsService;
        this.marketService = marketService;
        this.roleService = roleService;
    }

    @GetMapping
    ResponseEntity<List<UserMarketRoleDTO>> getUserMarketRoles(@CookieValue(value = "marketId", defaultValue = "null") String cookie) {
        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);
        List<UserMarketRoleDTO> userMarketRoles =
                userMarketRoleService.findByMarketId(marketId);

        return ResponseEntity.ok(userMarketRoles);
    }


    @PostMapping
    public ResponseEntity<String> assignRole(@RequestBody AssignRoleRequest assignRoleRequest,
                                             @CookieValue(value = "marketId", defaultValue = "null") String cookie) {
        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);
        Optional<Role> role = roleService.findByName(ERole.valueOf(assignRoleRequest.getRoleName()));
        Optional<Market> market = marketService.findById(marketId);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(assignRoleRequest.getUsername());
        User user = userDetailsService.loadUserById(userDetails.getId());

        if (role.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el rol");
        if (market.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el market");

        userMarketRoleService.assignRoleToUser(user, market.get(), role.get());

        return ResponseEntity.status(HttpStatus.OK).body("Rol asignado correctamente");
    }
    @PutMapping
    public  ResponseEntity<String> unassignRole (@RequestBody UnassignRoleRequest unassignRoleRequest,
                                                 @CookieValue(value = "marketId", defaultValue = "null") String cookie) {
        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);
        Optional<Role> role = roleService.findByName(ERole.valueOf(unassignRoleRequest.getRoleName()));
        Optional<Market> market = marketService.findById(marketId);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(unassignRoleRequest.getUsername());
        User user = userDetailsService.loadUserById(userDetails.getId());

        if (role.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el rol");
        if (market.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el market");

        boolean isUnassigned =
                userMarketRoleService.unassignRoleToUser(user, market.get(), role.get());
        if (isUnassigned) return ResponseEntity.ok("Rol desasignado correctamente");
        else return ResponseEntity.badRequest().body("No desasignado correctamente");
    }
}
