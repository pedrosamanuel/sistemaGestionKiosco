package com.inventario_ms.Order;

import com.inventario_ms.PriceList.PriceList;
import com.inventario_ms.PriceList.PriceListProduct;
import com.inventario_ms.PriceList.PriceListService;
import com.inventario_ms.Product.Product;
import com.inventario_ms.Product.ProductService;
import com.inventario_ms.Supplier.SupplierService;
import com.inventario_ms.Util.ExcelHelper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderExcelService {
    private final SupplierService supplierService;
    private final ProductService productService;
    private final PriceListService priceListService;

    public OrderExcelService(SupplierService supplierService, ProductService productService, PriceListService priceListService) {
        this.supplierService = supplierService;
        this.productService = productService;
        this.priceListService = priceListService;
    }

    public byte[] generateExcel(Long supplierId, OrderRequest orderRequest) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("OrdenDeCompra");

        createHeader(workbook, sheet, supplierId);

        String[] column = {"idProducto", "marcaProducto", "descripcionProducto","stockActual", "stockMinimo","faltante", "precioUnit", "cantidad p/precioUnit",
                "esPromocion","cantidadComprada", "total"};
        Row headerRow = sheet.createRow(3);
        ExcelHelper.createHeader(column, headerRow);
        ExcelHelper.autoSizeColumns(sheet, column.length);

        Set<Long> addedIds = new HashSet<>();
        if (orderRequest.isAllProducts()) {
            addAllProducts(sheet, supplierId);
        }
        if(orderRequest.isBelowMinStock()){
            addBellowMinStock(sheet,supplierId,addedIds);
        }
        if (orderRequest.isDiscountProducts()){
            addDiscountProduct(sheet,supplierId,addedIds);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }

    public void createHeader(XSSFWorkbook workbook, XSSFSheet sheet, Long supplierId) {

        // Crear fila combinada y centrada "Orden de Compra"
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Orden de Compra");

        // Combinar las primeras 6 columnas para la fila del título
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

        // Aplicar estilo centrado
        titleCell.setCellStyle(ExcelHelper.createHeaderStyle(workbook));

        // Crear fila "Proveedor:"
        Row supplierRow = sheet.createRow(1);
        Cell supplierCell = supplierRow.createCell(0);
        supplierCell.setCellValue("Proveedor:");

        Cell supplierNameCell = supplierRow.createCell(1);
        supplierNameCell.setCellValue(supplierService.findById(supplierId).orElseThrow().getNombre());
    }

    public void addAllProducts(XSSFSheet sheet, Long supplierId) {
        List<Product> products = productService.findBySupplierId(supplierId);
        PriceList priceList = priceListService.findBySupplierAndDate(supplierId);
        List<PriceListProduct> priceListProducts = priceList.getPriceListProducts();
        int rowNum = 4;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getMarca());
            row.createCell(2).setCellValue(product.getDescripcion());
            row.createCell(3).setCellValue(product.getStockActual());
            row.createCell(4).setCellValue(product.getStockMinimo());
            Cell cell5 = row.createCell(5);
            cell5.setCellFormula("IF(D" + rowNum + " > C" + rowNum + ", D" + rowNum + " - C" + rowNum + ", 0)");
            for (PriceListProduct priceListProduct : priceListProducts){
                if(priceListProduct.getProduct().equals(product)){
                    row.createCell(6).setCellValue(priceListProduct.getPrecio());
                    row.createCell(7).setCellValue(priceListProduct.getCantidad());
                    row.createCell(8).setCellValue(priceListProduct.isPromocion() ? "Si" : "No");
                }
            }
            Cell cell10 = row.createCell(10);
            cell10.setCellFormula("E" + rowNum + " * F" + rowNum);

        }
    }
    public void addBellowMinStock(XSSFSheet sheet, Long supplierId, Set<Long> addedIds) {
        List<Product> products = productService.findBySupplierIdAndStock(supplierId);
        PriceList priceList = priceListService.findBySupplierAndDate(supplierId);
        List<PriceListProduct> priceListProducts = priceList.getPriceListProducts();
        int rowNum = 4;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            boolean isAdded = addedIds.add(product.getId());
            if (!isAdded) continue;

            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getMarca());
            row.createCell(2).setCellValue(product.getDescripcion());
            row.createCell(3).setCellValue(product.getStockActual());
            row.createCell(4).setCellValue(product.getStockMinimo());
            Cell cell5 = row.createCell(5);
            cell5.setCellFormula("IF(D" + rowNum + " > C" + rowNum + ", D" + rowNum + " - C" + rowNum + ", 0)");
            for (PriceListProduct priceListProduct : priceListProducts){
                if(priceListProduct.getProduct().equals(product)){
                    row.createCell(6).setCellValue(priceListProduct.getPrecio());
                    row.createCell(7).setCellValue(priceListProduct.getCantidad());
                    row.createCell(8).setCellValue(priceListProduct.isPromocion() ? "Si" : "No");
                }
            }
            Cell cell10 = row.createCell(10);
            cell10.setCellFormula("G" + rowNum + " * J" + rowNum);

        }
    }
    private void addDiscountProduct(XSSFSheet sheet, Long supplierId, Set<Long> addedIds) {
        PriceList priceList = priceListService.findBySupplierAndDate(supplierId);
        List<PriceListProduct> priceListProducts = priceList.getPriceListProducts();
        int rowNum = 4;
        for (PriceListProduct priceListProduct : priceListProducts){
                Product product = priceListProduct.getProduct();
                boolean isAdded = addedIds.add(product.getId());
                if (!isAdded) continue;
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getMarca());
                row.createCell(2).setCellValue(product.getDescripcion());
                row.createCell(3).setCellValue(product.getStockActual());
                row.createCell(4).setCellValue(product.getStockMinimo());
                Cell cell5 = row.createCell(5);
                cell5.setCellFormula("IF(D" + rowNum + " > C" + rowNum + ", D" + rowNum + " - C" + rowNum + ", 0)");
                row.createCell(6).setCellValue(priceListProduct.getPrecio());
                row.createCell(7).setCellValue(priceListProduct.getCantidad());
                row.createCell(8).setCellValue(priceListProduct.isPromocion() ? "Si" : "No");
            Cell cell10 = row.createCell(10);
            cell10.setCellFormula("E" + rowNum + " * F" + rowNum);
        }
    }

}
