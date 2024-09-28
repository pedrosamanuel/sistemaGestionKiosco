package com.inventario_ms.Category.service;

import com.inventario_ms.Category.domain.Category;
import com.inventario_ms.Category.dto.CategoryDTO;
import com.inventario_ms.Category.repository.CategoryRepository;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericRepository;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.repository.MarketProductRepository;
import com.inventario_ms.Market.service.MarketService;
import com.inventario_ms.Product.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService extends MarketDependentGenericService<Category, CategoryDTO, CategoryRepository,Long> {
    private final MarketService marketService;
    private final CategoryRepository categoryRepository;
    private final MarketProductRepository marketProductRepository;
    public CategoryService(CategoryRepository repository, MarketService marketService, CategoryRepository categoryRepository, ProductService productService, MarketProductRepository marketProductRepository) {
        super(repository);
        this.marketService = marketService;
        this.categoryRepository = categoryRepository;
        this.marketProductRepository = marketProductRepository;
    }

    @Override
    protected Category setMarket(Category entity, Long marketId) {
        entity.setMarket(marketService.findById(marketId).get());
        return entity;
    }

    @Override
    protected Category updateEntity(Category entity, Category updatedEntity) {
        entity.setNombre(updatedEntity.getNombre());
        entity.setCostoMultiplicador(updatedEntity.getCostoMultiplicador());
        return entity;
    }

    @Override
    protected CategoryDTO convertToDTO(Category entity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNombre(entity.getNombre());
        categoryDTO.setId(entity.getId());
        categoryDTO.setCostoMultiplicador(entity.getCostoMultiplicador());
        return categoryDTO;
    }
    @Override
    @Transactional
    public Boolean deleteById(Long marketId, Long categoryId) {
        if (marketProductRepository.existsByCategoryId(categoryId)) {
            throw new IllegalStateException("No se puede eliminar la categoría porque hay productos asociados.");
        }
        Optional<Category> existingEntity = categoryRepository.findByIdAndMarketId(categoryId, marketId);
        if (existingEntity.isPresent()) {
            categoryRepository.deleteByIdAndMarketId(categoryId, marketId);
            return true;
        }
        return false;
    }
}
