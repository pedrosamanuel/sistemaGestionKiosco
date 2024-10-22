package com.inventario_ms.Security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Market.domain.Market;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMarketRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    public UserMarketRole(User user, Market market, Role role) {
        this.user = user;
        this.market = market;
        this.role = role;
        this.isActive = true;
    }
}
