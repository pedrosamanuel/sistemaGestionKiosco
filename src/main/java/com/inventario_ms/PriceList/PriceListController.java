package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.GenericController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/priceList")
public class PriceListController extends GenericController<PriceList, PriceListDTO, Long> {
    private final PriceListService priceListService;
    private final PriceListExcelService priceListExcelService;
    public PriceListController (PriceListService priceListService, PriceListExcelService priceListExcelService){
        super(priceListService);
        this.priceListService = priceListService;
        this.priceListExcelService = priceListExcelService;
    }

    @PostMapping("/{supplierId}/upload")
    public ResponseEntity<PriceListDTO> uploadSupplierPrices(@PathVariable("supplierId") Long supplierId,
                                               @RequestParam("file") MultipartFile file) {
        PriceListDTO priceListDTO = priceListService.uploadPriceList(supplierId, file);
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
