package com.inventario_ms.Proveedor;

import com.inventario_ms.Generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/provider")
public class ProviderController extends GenericController<Provider,Long> {
    public ProviderController(ProviderService providerService) {
        super(providerService);
    }
}
