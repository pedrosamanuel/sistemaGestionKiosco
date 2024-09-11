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
    private final PriceListService priceListService;

    public OrderExcelService(SupplierService supplierService, ProductService productService, PriceListService priceListService) {
        this.supplierService = supplierService;
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
        int rowNum = 4;

        if (orderRequest.isAllProducts()) {
            rowNum = addAllProducts(sheet, supplierId, rowNum, addedIds);
        }
        if(orderRequest.isBelowMinStock()){
            rowNum = addBellowMinStock(sheet,supplierId, rowNum, addedIds);
        }
        if (orderRequest.isDiscountProducts()){
           addDiscountProduct(sheet,supplierId, rowNum, addedIds);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }
    private void createHeader(XSSFWorkbook workbook, XSSFSheet sheet, Long supplierId) {
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

    private int addAllProducts(XSSFSheet sheet, Long supplierId, int rowNum, Set<Long> addedIds) {
        PriceList priceList = priceListService.findBySupplierAndDate(supplierId);
        List<PriceListProduct> priceListProducts = priceList.getPriceListProducts();
        for (PriceListProduct priceListProduct : priceListProducts){
            rowNum = addProductToSheet(priceListProduct, sheet, rowNum);
        }
        return rowNum;
    }
    private int addBellowMinStock(XSSFSheet sheet, Long supplierId, int rowNum, Set<Long> addedIds) {
        PriceList priceList = priceListService.findBySupplierAndDate(supplierId);
        List<PriceListProduct> priceListProducts = priceList.getPriceListProducts();

        for (PriceListProduct priceListProduct : priceListProducts){
            if(priceListProduct.getProduct().getStockMinimo() <
                    priceListProduct.getProduct().getStockActual()) continue; //si el stockActual esta encima del minimo pasa al siguiente
            boolean isAdded = addedIds.add(priceListProduct.getId());
            if(!isAdded) continue; //si no esta añadido pasa al siguiente
            rowNum = addProductToSheet(priceListProduct, sheet, rowNum);
        }
        return rowNum;
    }
    private void addDiscountProduct(XSSFSheet sheet, Long supplierId, int rowNum, Set<Long> addedIds) {
        PriceList priceList = priceListService.findBySupplierAndDate(supplierId);
        List<PriceListProduct> priceListProducts = priceList.getPriceListProducts();

        for (PriceListProduct priceListProduct : priceListProducts){
            if(!priceListProduct.isPromocion()) continue; // si no esta en promocion pasa al siguiente
            boolean isAdded = addedIds.add(priceListProduct.getId());
            if(!isAdded) continue;  //si ya esta añadido pasa al siguiente
            rowNum = addProductToSheet(priceListProduct, sheet, rowNum);
        }
    }
    private int addProductToSheet(PriceListProduct priceListProduct, XSSFSheet sheet, int rowNum){
        Product product = priceListProduct.getProduct();
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(product.getId());
        row.createCell(1).setCellValue(product.getMarca());
        row.createCell(2).setCellValue(product.getDescripcion());
        row.createCell(3).setCellValue(product.getStockActual());
        row.createCell(4).setCellValue(product.getStockMinimo());

        Cell cell5 = row.createCell(5);
        cell5.setCellFormula("IF(E" + rowNum + " > D" + rowNum + ", E" + rowNum + " - D" + rowNum + ", 0)");

        row.createCell(6).setCellValue(priceListProduct.getPrecio());
        row.createCell(7).setCellValue(priceListProduct.getCantidad());
        row.createCell(8).setCellValue(priceListProduct.isPromocion() ? "Si" : "No");

        Cell cell10 = row.createCell(10);
        cell10.setCellFormula("G" + rowNum + " * J" + rowNum);
        return rowNum;
    }
}
