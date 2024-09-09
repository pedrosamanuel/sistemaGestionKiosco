package com.inventario_ms.Util;

import com.inventario_ms.Product.Product;
import com.inventario_ms.Product.ProductRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    private final ProductRepository productRepository;

    public ExcelService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public byte[] generateExcel(Long supplierId) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        createValidity(workbook);

        createProduct(workbook, supplierId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }

    private void createValidity(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("Vigencia");
        Row headerRow = sheet.createRow(0);

        Row row = sheet.createRow(1);
        Cell cell10 = row.createCell(0);
        Cell cell11 = row.createCell(1);

        CreationHelper createHelper = workbook.getCreationHelper();

        CellStyle bottomLeftCellStyle = workbook.createCellStyle();
        bottomLeftCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        CellStyle bottomRightCellStyle = workbook.createCellStyle();
        bottomRightCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        cell10.setCellStyle(bottomLeftCellStyle);
        cell11.setCellStyle(bottomRightCellStyle);

        String[] columnas = {"Fecha inicio vigencia", "Fecha fin Vigencia"};
        createHeader(columnas, headerRow);

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createProduct(XSSFWorkbook workbook, Long supplierId) {
        XSSFSheet sheet = workbook.createSheet("Articulos");
        Row headerRow = sheet.createRow(0);

        String[] columnas = {"idArticulo", "marca", "descripcion", "precio","cantidad", "promocion"};
        createHeader(columnas, headerRow);

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }
        List<Product> products = productRepository.findBySupplierId(supplierId);


        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);

            // Rellenar las celdas de la fila con los valores del producto
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getMarca());
            row.createCell(2).setCellValue(product.getDescripcion());
            row.createCell(5).setCellValue("No");

        }
        addValidation(sheet, rowNum);
    }

    private void createHeader(String[] columnas, Row headerRow) {
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);

            CellStyle headerStyle = headerRow.getSheet().getWorkbook().createCellStyle();
            Font font = headerRow.getSheet().getWorkbook().createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            cell.setCellStyle(headerStyle);
        }
    }
    private void addValidation(XSSFSheet sheet, int rowCount) {

        CellRangeAddressList addressList = new CellRangeAddressList(1, rowCount, 5, 5);


        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(new String[] {"Si", "No"});
        DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);


        dataValidation.setShowErrorBox(true);
        sheet.addValidationData(dataValidation);
    }
}