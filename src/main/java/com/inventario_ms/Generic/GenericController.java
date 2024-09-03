package com.inventario_ms.Generic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

public abstract class GenericController<T,DTO, ID> {

    private final GenericService<T, DTO, ID> service;

    public GenericController(GenericService<T,DTO,ID> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable ID id) {
        Optional<DTO> entity = service.findById(id);
        return entity.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody T entity) {
        service.save(entity);
        return ResponseEntity.ok("Entidad Creada");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable ID id, @RequestBody T entity) {
        Boolean isUpdated = service.update(id,entity);
        if (isUpdated) {
            return ResponseEntity.ok("Entidad Actualizada");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable ID id) {
        Boolean isDeleted = service.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Entidad Eliminada");
        }
        return ResponseEntity.notFound().build();
    }
}
