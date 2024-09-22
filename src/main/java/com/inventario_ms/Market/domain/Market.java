package com.inventario_ms.Market.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario_ms.Category.domain.Category;
import com.inventario_ms.Generic.BaseEntity;
import com.inventario_ms.Market.dto.MarketDTO;
import com.inventario_ms.Sale.domain.Sale;
import com.inventario_ms.Security.model.User;
import com.inventario_ms.Supplier.domain.Supplier;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Market extends BaseEntity<Long> {
    private String name;
    @OneToMany(mappedBy = "market")
    private List<MarketProduct> marketProduct = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "market")
    @JsonIgnore
    private List<Sale> sales = new ArrayList<>();
    @OneToMany(mappedBy = "market")
    @JsonIgnore
    private List<Supplier> suppliers = new ArrayList<>();
    @OneToMany(mappedBy = "market")
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

}


