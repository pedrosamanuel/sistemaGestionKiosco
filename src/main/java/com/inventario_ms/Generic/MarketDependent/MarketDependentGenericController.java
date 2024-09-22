package com.inventario_ms.Generic.MarketDependent;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


public abstract class MarketDependentGenericController<T, DTO,
        S extends MarketDependentGenericService<T,DTO,R,ID>,
        R extends MarketDependentGenericRepository<T,ID>, ID>{

    private final S service;
    public MarketDependentGenericController(S service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<DTO>> getAll(HttpSession session) {
        Long marketId = (Long) session.getAttribute("selectedMarketId");
        if (marketId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(service.findAll(marketId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable ID id, HttpSession session) {
        Long marketId = (Long) session.getAttribute("selectedMarketId");

        if (marketId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Optional<DTO> entity = service.findByIdDTO(marketId, id);
        return entity.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody T entity, HttpSession session) {
        Long marketId = (Long) session.getAttribute("selectedMarketId");

        if (marketId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        service.save(marketId, entity);
        return ResponseEntity.ok("Entidad Creada");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable ID id, @RequestBody T entity, HttpSession session) {
        Long marketId = (Long) session.getAttribute("selectedMarketId");

        if (marketId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Boolean isUpdated = service.update(marketId, id, entity);
        if (isUpdated) {
            return ResponseEntity.ok("Entidad Actualizada");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable ID id, HttpSession session) {
        Long marketId = (Long) session.getAttribute("selectedMarketId");

        if (marketId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Boolean isDeleted = service.deleteById(marketId, id);
        if (isDeleted) {
            return ResponseEntity.ok("Entidad Eliminada");
        }
        return ResponseEntity.notFound().build();
    }
}
