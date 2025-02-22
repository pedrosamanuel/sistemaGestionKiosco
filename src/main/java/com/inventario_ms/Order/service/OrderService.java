package com.inventario_ms.Order.service;

import com.inventario_ms.Common.ExcelHelper;
import com.inventario_ms.Generic.MarketDependent.MarketDependentGenericService;
import com.inventario_ms.Order.domain.Order;
import com.inventario_ms.Order.domain.OrderProduct;
import com.inventario_ms.Order.dto.OrderDTO;
import com.inventario_ms.Order.event.OrderEvent;
import com.inventario_ms.Order.repository.OrderRepository;
import com.inventario_ms.Product.service.ProductService;
import com.inventario_ms.Supplier.service.SupplierService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService  {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final OrderRepository orderRepository;
    private final SupplierService supplierService;
    private final ProductService productService;

    public OrderService(ApplicationEventPublisher applicationEventPublisher,
                        OrderRepository orderRepository,
                        SupplierService supplierService,
                        ProductService productService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.orderRepository = orderRepository;
        this.supplierService = supplierService;
        this.productService = productService;
    }


    public byte[] processAndGenerateExcel(Long supplierId, MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        Order order = Order.builder()
                .fechaOrdenDeCompra(LocalDateTime.now())
                .supplier(supplierService.findById(supplierId).orElseThrow())
                .build();
        List<OrderProduct> products = new ArrayList<>();


        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            OrderProduct orderProduct = extractOrderProductFromRow(row);
            products.add(orderProduct);
        }

        order.setProducts(products);

        applicationEventPublisher.publishEvent(new OrderEvent(this, order.getProducts(), order.getSupplier().getMarket().getId()));

        orderRepository.save(order);


        byte[] newExcel = generateReducedExcel(products, file);

        return newExcel;
    }

    private OrderProduct extractOrderProductFromRow(Row row) {
        return OrderProduct.builder()
                .product(productService.findByCodigo(row.getCell(0).getStringCellValue()).orElseThrow())
                .precioUnitario(row.getCell(6).getNumericCellValue())
                .cantidad((int) row.getCell(9).getNumericCellValue())
                .build();
    }

    private byte[] generateReducedExcel(List<OrderProduct> products, MultipartFile file) throws IOException {
        // Cargar el archivo existente
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);

        int lastRowNum = sheet.getLastRowNum();
        for (int i = 3; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                sheet.removeRow(row);
            }
        }

        String[] columns = {"Descripción", "Precio Unitario", "Cantidad", "Subtotal"};
        Row headerRow = sheet.createRow(3);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowNum = 4;
        double total = 0.0;

        for (OrderProduct orderProduct : products) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(orderProduct.getProduct().getDescripcion()); // Descripción
            row.createCell(1).setCellValue(orderProduct.getPrecioUnitario()); // Precio Unitario
            row.createCell(2).setCellValue(orderProduct.getCantidad()); // Cantidad

            double subtotal = orderProduct.getPrecioUnitario() * orderProduct.getCantidad();
            row.createCell(3).setCellValue(subtotal);

            total += subtotal;

        }

        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(2).setCellValue("Total:");
        totalRow.createCell(3).setCellValue(total);

        ExcelHelper.autoSizeColumns(sheet, columns.length);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }
}

