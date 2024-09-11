package com.inventario_ms.Order;

import com.inventario_ms.Generic.GenericService;
import com.inventario_ms.Product.ProductService;
import com.inventario_ms.Supplier.SupplierService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService extends GenericService<Order, OrderDTO, Long> {
    private final OrderRepository orderRepository;
    private final SupplierService supplierService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, SupplierService supplierService, ProductService productService) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.supplierService = supplierService;
        this.productService = productService;
    }

    @Override
    protected Order updateEntity(Order entity, Order updatedEntity) {
        entity.setFechaOrdenDeCompra(updatedEntity.getFechaOrdenDeCompra());
        entity.setProducts(updatedEntity.getProducts());
        entity.setSupplier(updatedEntity.getSupplier());
        return entity;
    }

    @Override
    protected OrderDTO convertToDTO(Order entity) {
        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setFechaOrdenDeCompra(entity.getFechaOrdenDeCompra());
        dto.setProducts(entity.getProducts());
        dto.setSupplier(entity.getSupplier());
        return dto;
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

        orderRepository.save(order);


        byte[] newExcel = generateReducedExcel(products, file);

        return newExcel;
    }

    private OrderProduct extractOrderProductFromRow(Row row) {
        return OrderProduct.builder()
                .product(productService.findById((long) row.getCell(0).getNumericCellValue()).orElseThrow())
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

        String[] columns = {"Marca", "Descripción", "Precio Unitario", "Cantidad", "Subtotal"};
        Row headerRow = sheet.createRow(3);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowNum = 4;
        double total = 0.0;

        for (OrderProduct orderProduct : products) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(orderProduct.getProduct().getMarca()); // Marca
            row.createCell(1).setCellValue(orderProduct.getProduct().getDescripcion()); // Descripción
            row.createCell(2).setCellValue(orderProduct.getPrecioUnitario()); // Precio Unitario
            row.createCell(3).setCellValue(orderProduct.getCantidad()); // Cantidad

            double subtotal = orderProduct.getPrecioUnitario() * orderProduct.getCantidad();
            row.createCell(4).setCellValue(subtotal);

            total += subtotal;

        }

        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(3).setCellValue("Total:");
        totalRow.createCell(4).setCellValue(total);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }
}

