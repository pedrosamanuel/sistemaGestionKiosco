package com.inventario_ms.Generic.MarketDependent;


import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.service.MarketService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MarketDependentGenericService<T,DTO, R extends MarketDependentGenericRepository<T,ID>, ID> {

    private final R repository;

    public MarketDependentGenericService(R repository) {
        this.repository = repository;
    }

    public List<DTO> findAll(Long marketId) {
        List<T> list = repository.findAllByMarketId(marketId);
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<DTO> findByIdDTO(Long marketId , ID id) {
        Optional<T> entity = repository.findByIdAndMarketId(id, marketId);
        return entity.map(this::convertToDTO);
    }
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }


    public void save(Long marketId, T entity) {
        repository.save(setMarket(entity, marketId));
    }
    @Transactional
    public Boolean deleteById(Long marketId, ID id) {
        Optional<T> existingEntity = repository.findByIdAndMarketId(id, marketId);
        if (existingEntity.isPresent()) {
            repository.deleteByIdAndMarketId(id, marketId);
            return true;
        }
        return false;
    }

    public Boolean update(Long marketId, ID id, T updatedEntity) {
        Optional<T> existingEntity = repository.findByIdAndMarketId(id, marketId);
        if (existingEntity.isPresent()) {
            T entity = existingEntity.get();
            T updated = updateEntity(entity, updatedEntity);
            repository.save(updated);
            return true;
        }
        return false;
    }
    protected abstract T setMarket(T entity, Long marketId);
    protected abstract T updateEntity(T entity, T updatedEntity);
    protected abstract DTO convertToDTO(T entity);
}