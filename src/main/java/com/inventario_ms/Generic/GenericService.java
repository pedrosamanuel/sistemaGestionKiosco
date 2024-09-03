package com.inventario_ms.Generic;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class GenericService<T,DTO, ID> {

    private final GenericRepository<T, ID> repository;

    public GenericService(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }
    public List<DTO> findAll() {
        List<T> list = repository.findAll();
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<DTO> findById(ID id) {
        Optional<T> entity = repository.findById(id);
        return entity.map(this::convertToDTO);
    }

    public void save(T entity) {
        repository.save(entity);
    }

    public Boolean deleteById(ID id) {
        Optional<T> existingEntity = repository.findById(id);
        if (existingEntity.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean update(ID id, T updatedEntity) {
        Optional<T> existingEntity = repository.findById(id);
        if (existingEntity.isPresent()) {
            T entity = existingEntity.get();
            T updated = updateEntity(entity, updatedEntity);
            repository.save(updated);
            return true;
        }
        return false;
    }

    protected abstract T updateEntity(T entity, T updatedEntity);
    protected abstract DTO convertToDTO(T entity);
}