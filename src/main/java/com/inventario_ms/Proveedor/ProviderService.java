package com.inventario_ms.Proveedor;

import com.inventario_ms.Generic.GenericRepository;
import com.inventario_ms.Generic.GenericService;
import org.springframework.stereotype.Service;

@Service
public class ProviderService extends GenericService<Provider,Long> {
    public ProviderService(ProviderRepository providerRepository) {
        super(providerRepository);
    }

    @Override
    protected Provider updateEntity(Provider entity, Provider updatedEntity) {
        entity.setCodProveedor(updatedEntity.getCodProveedor());
        entity.setNombre(updatedEntity.getNombre());
        return entity;
    }
}
