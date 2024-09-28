package com.inventario_ms.Order.service;

import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Market.service.MarketProductService;
import com.inventario_ms.Market.service.MarketService;
import com.inventario_ms.Order.dto.OrderRequest;
import com.inventario_ms.PriceList.domain.PriceList;
import com.inventario_ms.PriceList.domain.PriceListProduct;
import com.inventario_ms.PriceList.service.PriceListService;
import com.inventario_ms.Product.domain.Product;
import com.inventario_ms.Product.service.ProductService;
import com.inventario_ms.Supplier.service.SupplierService;
import com.inventario_ms.Common.ExcelHelper;
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
    private final MarketProductService marketProductService;

    public OrderExcelService(SupplierService supplierService, PriceListService priceListService,
                             MarketProductService marketProductService) {
        this.supplierService = supplierService;
        this.priceListService = priceListService;
        this.marketProductService = marketProductService;
    }

    public byte[] generateExcel(Long supplierId, Long marketId,  OrderRequest orderRequest) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("OrdenDeCompra");

        createHeader(workbook, sheet, supplierId);

        String[] column = {"codProducto", "descripcionProducto","stockActual", "stockMinimo","faltante", "precioUnit", "cantidad p/precioUnit",
                "esPromocion","cantidadComprada", "total"};
        Row headerRow = sheet.createRow(3);
        ExcelHelper.createHeader(column, headerRow);
        ExcelHelper.autoSizeColumns(sheet, column.length);

        Set<Long> addedIds = new HashSet<>();
        int rowNum = 4;

        if (orderRequest.isAllProducts()) {
            rowNum = addAllProducts(sheet, supplierId,marketId , rowNum, addedIds);
        }
        if(orderRequest.isBelowMinStock()){
            rowNum = addBellowMinStock(sheet,supplierId, marketId, rowNum, addedIds);
        }
        if (orderRequest.isDiscountProducts()){
           addDiscountProduct(sheet,supplierId,marketId, rowNum, addedIds);
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

    private int addAllProducts(XSSFSheet sheet, Long supplierId, Long marketId, int rowNum, Set<Long> addedIds) {
        PriceList priceList = priceListService.findBySupplierAndDate(supplierId);
        List<PriceListProduct> priceListProducts = priceList.getPriceListProducts();
        for (PriceListProduct priceListProduct : priceListProducts){
            rowNum = addProductToSheet(priceListProduct, marketId, sheet, rowNum);
        }
        return rowNum;
    }
    private int addBellowMinStock(XSSFSheet sheet, Long supplierId,Long marketId, int rowNum, Set<Long> addedIds) {
        PriceList priceList = priceListService.findBySupplierAndDate(supplierId);
        List<PriceListProduct> priceListProducts = priceList.getPriceListProducts();


        for (PriceListProduct priceListProduct : priceListProducts){
            MarketProduct marketProduct =
                    marketProductService.findByMarketIdAndProductId(marketId,priceListProduct.getProduct().getId());
            if(marketProduct.getStockMinimo() <
                  marketProduct.getStockActual()) continue; //si el stockActual esta encima del minimo pasa al siguiente
            boolean isAdded = addedIds.add(priceListProduct.getId());
            if(!isAdded) continue; //si no esta añadido pasa al siguiente
            rowNum = addProductToSheet(priceListProduct,marketId, sheet, rowNum);
        }
        return rowNum;
    }
    private void addDiscountProduct(XSSFSheet sheet, Long supplierId, Long marketId,int rowNum, Set<Long> addedIds) {
        PriceList priceList = priceListService.findBySupplierAndDate(supplierId);
        List<PriceListProduct> priceListProducts = priceList.getPriceListProducts();

        for (PriceListProduct priceListProduct : priceListProducts){
            if(!priceListProduct.isPromocion()) continue; // si no esta en promocion pasa al siguiente
            boolean isAdded = addedIds.add(priceListProduct.getId());
            if(!isAdded) continue;  //si ya esta añadido pasa al siguiente
            rowNum = addProductToSheet(priceListProduct,marketId, sheet, rowNum);
        }
    }
    private int addProductToSheet(PriceListProduct priceListProduct, Long marketId, XSSFSheet sheet, int rowNum){
        Product product = priceListProduct.getProduct();
        MarketProduct marketProduct =
                marketProductService.findByMarketIdAndProductId(marketId,priceListProduct.getProduct().getId());
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(product.getCodigo());
        row.createCell(1).setCellValue(product.getDescripcion());
        row.createCell(2).setCellValue(marketProduct.getStockActual());
        row.createCell(3).setCellValue(marketProduct.getStockMinimo());

        Cell cell5 = row.createCell(4);
        cell5.setCellFormula("IF(D" + rowNum + " > C" + rowNum + ", D" + rowNum + " - C" + rowNum + ", 0)");

        row.createCell(5).setCellValue(priceListProduct.getPrecio());
        row.createCell(6).setCellValue(priceListProduct.getCantidad());
        row.createCell(7).setCellValue(priceListProduct.isPromocion() ? "Si" : "No");

        Cell cell10 = row.createCell(9);
        cell10.setCellFormula("F" + rowNum + " * I" + rowNum);
        return rowNum;
    }
}
