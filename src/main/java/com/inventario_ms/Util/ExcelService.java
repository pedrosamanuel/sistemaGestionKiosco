package com.inventario_ms.Util;

import com.inventario_ms.Product.Product;
import com.inventario_ms.Product.ProductRepository;
import com.inventario_ms.Product.ProductService;
import com.inventario_ms.Supplier.Supplier;
import com.inventario_ms.Supplier.SupplierService;
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
public class ExcelService {
    private final ProductService productService;
    private final SupplierService supplierService;

    public ExcelService(ProductService productService, SupplierService supplierService) {
        this.productService = productService;
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
        XSSFSheet sheet = workbook.createSheet("Articulos");

        // Crear fila combinada y centrada "Lista de precio"
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Lista de precio");

        // Combinar las primeras 6 columnas para la fila del título
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        // Aplicar estilo centrado
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);

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

        String[] column = {"idArticulo", "marca", "descripcion", "precio","cantidad", "promocion"};
        createHeader(column, headerRow);

        for (int i = 0; i < column.length; i++) {
            sheet.autoSizeColumn(i);
        }
        List<Product> products = productService.findBySupplierId(supplierId);


        int rowNum = 5;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getMarca());
            row.createCell(2).setCellValue(product.getDescripcion());
            row.createCell(5).setCellValue("No");

        }
        addValidation(sheet, rowNum);
    }

    private void createHeader(String[] column, Row headerRow) {
        for (int i = 0; i < column.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(column[i]);

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