package com.inventario_ms.Generic.NonDependent;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NonDependentGenericRepository<T, ID> extends JpaRepository<T, ID> {
}