package com.inventario_ms.Generic.MarketDependent;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public abstract class MarketDependentGenericController<T, DTO,
        S extends MarketDependentGenericService<T,DTO,R,ID>,
        R extends MarketDependentGenericRepository<T,ID>, ID>{

    private final S service;
    public MarketDependentGenericController(S service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<DTO>> getAll( @CookieValue(value = "marketId",defaultValue = "null") String cookie) {
        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);

        return ResponseEntity.ok(service.findAll(marketId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable ID id, @CookieValue(value = "marketId",defaultValue = "null") String cookie) {
        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);

        Optional<DTO> entity = service.findByIdDTO(marketId, id);
        return entity.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody T entity, @CookieValue(value = "marketId",defaultValue = "null") String cookie) {
        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);
        service.save(marketId, entity);
        return ResponseEntity.ok("Entidad Creada");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable ID id, @RequestBody T entity,
                                         @CookieValue(value = "marketId",defaultValue = "null") String cookie) {

        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);
        Boolean isUpdated = service.update(marketId, id, entity);
        if (isUpdated) {
            return ResponseEntity.ok("Entidad Actualizada");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable ID id, @CookieValue(value = "marketId",defaultValue = "null") String cookie) {
        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);

        Boolean isDeleted = service.deleteById(marketId, id);
        if (isDeleted) {
            return ResponseEntity.ok("Entidad Eliminada");
        }
        return ResponseEntity.notFound().build();
    }
}
