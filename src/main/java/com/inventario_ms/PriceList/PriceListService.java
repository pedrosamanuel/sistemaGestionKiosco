package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.GenericRepository;
import com.inventario_ms.Generic.GenericService;
import com.inventario_ms.Supplier.Supplier;
import com.inventario_ms.Supplier.SupplierProduct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceListService extends GenericService<PriceList,PriceListDTO,Long> {
    public PriceListService(PriceListRepository priceListRepository) {
        super(priceListRepository);
    }

    @Override
    protected PriceList updateEntity(PriceList entity, PriceList updatedEntity) {
        return null;
    }

    @Override
    protected PriceListDTO convertToDTO(PriceList entity) {
        PriceListDTO dto = new PriceListDTO();
        dto.setId(entity.getId());
        dto.setFechaFinvigencia(entity.getFechaFinvigencia());
        dto.setFechaInicioVigencia(entity.getFechaInicioVigencia());
        dto.setSupplier(entity.getSupplier());
        dto.setPriceListProducts(entity.getPriceListProducts());
        return dto;
    }
}
