package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.GenericController;
import com.inventario_ms.Generic.GenericService;
import com.inventario_ms.Product.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/priceList")
public class PriceListController extends GenericController<PriceList, PriceListDTO, Long> {
    public PriceListController (PriceListService priceListService){
        super(priceListService);
    }
}
