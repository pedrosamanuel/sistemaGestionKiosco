package com.inventario_ms.PriceList.controller;

import com.inventario_ms.PriceList.dto.PriceListDTO;
import com.inventario_ms.PriceList.service.PriceListExcelService;
import com.inventario_ms.PriceList.service.PriceListService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/priceList")
public class PriceListController {
    private final PriceListService priceListService;
    private final PriceListExcelService priceListExcelService;
    public PriceListController (PriceListService priceListService, PriceListExcelService priceListExcelService){
        this.priceListService = priceListService;
        this.priceListExcelService = priceListExcelService;
    }

    @PostMapping("/{supplierId}/upload")
    public ResponseEntity<PriceListDTO> uploadSupplierPrices(@PathVariable("supplierId") Long supplierId,
                                                             @RequestParam("file") MultipartFile file,
                                                             @CookieValue(value = "marketId",defaultValue = "null") String cookie) {
        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);
        PriceListDTO priceListDTO = priceListService.uploadPriceList(marketId, supplierId, file);
        if (priceListDTO != null){
            return ResponseEntity.ok(priceListDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{supplierId}/download")
    public ResponseEntity<byte[]> downloadSupplierTemplate(@PathVariable Long supplierId) {
        try {
            byte[] excelContent = priceListExcelService.generateExcel(supplierId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "planilla_proveedor.xlsx");

            return new ResponseEntity<>(excelContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
