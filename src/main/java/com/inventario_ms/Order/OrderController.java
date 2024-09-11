package com.inventario_ms.Order;

import com.inventario_ms.Generic.GenericController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/order")
public class OrderController extends GenericController<Order,OrderDTO, Long> {
    private final OrderService orderService;
    private final OrderExcelService orderExcelService;
    public OrderController(OrderService orderService, OrderExcelService orderExcelService) {
        super(orderService);
        this.orderService = orderService;
        this.orderExcelService = orderExcelService;
    }

    @GetMapping("/{supplierId}/getOrderTemplate")
    public ResponseEntity<byte[]> getOrderTemplate(@PathVariable Long supplierId,
                                                   @RequestBody OrderRequest orderRequest){
        try {
            byte[] excelContent = orderExcelService.generateExcel(supplierId,orderRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "orden_de_compra.xlsx");

            return new ResponseEntity<>(excelContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping({"/{supplierId}/uploadOrder"})
    public ResponseEntity<byte[]> uploadOrder(@PathVariable Long supplierId,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        try {
            byte[] processedExcel = orderService.processAndGenerateExcel(supplierId, file);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "orden_proveedor.xlsx");

            return new ResponseEntity<>(processedExcel, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
