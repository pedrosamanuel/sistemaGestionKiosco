package com.inventario_ms.Category.controller;

import com.inventario_ms.Category.dto.CategoryDTO;
import com.inventario_ms.Category.domain.Category;
import com.inventario_ms.Category.repository.CategoryRepository;
import com.inventario_ms.Category.service.CategoryService;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericController;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/category")
public class CategoryController extends MarketDependentGenericController<Category, CategoryDTO,
        CategoryService, CategoryRepository, Long> {

    public CategoryController(CategoryService service) {
        super(service);
    }
}
