package com.inventario_ms.Order.controller;

import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericController;
import com.inventario_ms.Generic.NonDependent.NonDependentGenericController;
import com.inventario_ms.Order.dto.OrderDTO;
import com.inventario_ms.Order.service.OrderExcelService;
import com.inventario_ms.Order.dto.OrderRequest;
import com.inventario_ms.Order.service.OrderService;
import com.inventario_ms.Order.domain.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/order")
public class OrderController  {
    private final OrderService orderService;
    private final OrderExcelService orderExcelService;
    public OrderController(OrderService orderService, OrderExcelService orderExcelService) {
        this.orderService = orderService;
        this.orderExcelService = orderExcelService;
    }

    @PostMapping("/{supplierId}/getOrderTemplate")
    public ResponseEntity<byte[]> getOrderTemplate(@PathVariable Long supplierId,
                                                   @RequestBody OrderRequest orderRequest,
                                                   @CookieValue(value = "marketId",defaultValue = "null") String cookie) {
        if (cookie.equals("null")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Long marketId = Long.parseLong(cookie);
        try {
            byte[] excelContent = orderExcelService.generateExcel(supplierId, marketId, orderRequest);

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
