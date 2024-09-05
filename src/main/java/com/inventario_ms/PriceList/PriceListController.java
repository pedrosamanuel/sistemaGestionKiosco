package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.GenericController;
import com.inventario_ms.Generic.GenericService;
import com.inventario_ms.Product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/priceList")
public class PriceListController extends GenericController<PriceList, PriceListDTO, Long> {
    private final PriceListService priceListService;
    public PriceListController (PriceListService priceListService){
        super(priceListService);
        this.priceListService = priceListService;
    }

    @PostMapping("/{supplierId}/upload")
    public ResponseEntity<PriceListDTO> upload(@PathVariable("supplierId") Long supplierId,
                                               @RequestParam("file") MultipartFile file) {
        PriceListDTO priceListDTO = priceListService.uploadPriceList(supplierId, file);
        if (priceListDTO != null){
            return ResponseEntity.ok(priceListDTO);
        }
        return ResponseEntity.badRequest().build();
    }
}
