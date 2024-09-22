package com.inventario_ms.Category.service;

import com.inventario_ms.Category.domain.Category;
import com.inventario_ms.Category.dto.CategoryDTO;
import com.inventario_ms.Category.repository.CategoryRepository;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.service.MarketService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends MarketDependentGenericService<Category, CategoryDTO, CategoryRepository,Long> {
    private final MarketService marketService;
    public CategoryService(CategoryRepository repository, MarketService marketService) {
        super(repository);
        this.marketService = marketService;
    }

    @Override
    protected Category setMarket(Category entity, Long marketId) {
        entity.setMarket(marketService.findById(marketId).get());
        return entity;
    }

    @Override
    protected Category updateEntity(Category entity, Category updatedEntity) {
        entity.setNombre(updatedEntity.getNombre());
        return entity;
    }

    @Override
    protected CategoryDTO convertToDTO(Category entity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNombre(entity.getNombre());
        categoryDTO.setId(entity.getId());
        categoryDTO.setProducts(entity.getProducts());
        return categoryDTO;
    }
}
