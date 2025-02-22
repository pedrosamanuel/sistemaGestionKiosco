package com.inventario_ms.PriceList.service;

import com.inventario_ms.Market.domain.MarketProduct;
import com.inventario_ms.Market.domain.MarketProductSupplier;
import com.inventario_ms.Market.service.MarketProductService;
import com.inventario_ms.Product.domain.Product;
import com.inventario_ms.Product.service.ProductService;
import com.inventario_ms.Supplier.service.SupplierService;
import com.inventario_ms.Common.ExcelHelper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PriceListExcelService {
    private final MarketProductService marketProductService;
    private final SupplierService supplierService;

    public PriceListExcelService(MarketProductService marketProductService, SupplierService supplierService) {
        this.marketProductService = marketProductService;
        this.supplierService = supplierService;
    }

    public byte[] generateExcel(Long supplierId) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        createProduct(workbook, supplierId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }

    private void createProduct(XSSFWorkbook workbook, Long supplierId) {
        XSSFSheet sheet = workbook.createSheet("Productos");

        // Crear fila combinada y centrada "Lista de precio"
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Lista de precio");

        // Combinar las primeras 6 columnas para la fila del título
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        // Aplicar estilo centrado
        titleCell.setCellStyle(ExcelHelper.createHeaderStyle(workbook));

        // Crear fila "Proveedor:"
        Row supplierRow = sheet.createRow(1);
        Cell supplierCell = supplierRow.createCell(0);
        supplierCell.setCellValue("Proveedor:");

        Cell supplierNameCell = supplierRow.createCell(1);
        supplierNameCell.setCellValue(supplierService.findById(supplierId).orElseThrow().getNombre());


        // Crear fila "Vigencia hasta:"
        Row vigenciaRow = sheet.createRow(2);
        Cell vigenciaCell = vigenciaRow.createCell(0);
        vigenciaCell.setCellValue("Vigencia hasta:");

        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle vigenciaDateCellStyle = workbook.createCellStyle();
        vigenciaDateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        Cell vigenciaDateCell = vigenciaRow.createCell(1);
        vigenciaDateCell.setCellStyle(vigenciaDateCellStyle);

        Row headerRow = sheet.createRow(4);

        String[] column = {"codProducto", "descripcion", "precio","cantidad", "promocion"};
        ExcelHelper.createHeader(column, headerRow);


        List<MarketProduct> marketProducts = marketProductService.findBySupplierId(supplierId);


        int rowNum = 5;
        for (MarketProduct marketProduct : marketProducts) {
            Row row = sheet.createRow(rowNum++);
            Product product = marketProduct.getProduct();

            row.createCell(0).setCellValue(product.getCodigo());
            row.createCell(1).setCellValue(product.getDescripcion());
            row.createCell(4).setCellValue("No");

        }
        addValidation(sheet, rowNum);
        ExcelHelper.autoSizeColumns(sheet,column.length);
    }


    private void addValidation(XSSFSheet sheet, int rowCount) {

        CellRangeAddressList addressList = new CellRangeAddressList(1, rowCount, 4, 4);

        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(new String[] {"Si", "No"});
        DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);

        dataValidation.setShowErrorBox(true);
        sheet.addValidationData(dataValidation);
    }
}