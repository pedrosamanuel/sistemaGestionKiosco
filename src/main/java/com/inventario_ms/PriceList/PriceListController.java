package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.GenericController;
import com.inventario_ms.Generic.GenericService;
import com.inventario_ms.Product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/upload")
    public ResponseEntity<PriceListDTO> uploadPriceList(@RequestParam("file") MultipartFile file) {
        PriceListDTO priceListDTO = priceListService.uploadPriceList(file);
        if (priceListDTO != null){
            return ResponseEntity.ok(priceListDTO);
        }
        return ResponseEntity.badRequest().build();
    }
}
