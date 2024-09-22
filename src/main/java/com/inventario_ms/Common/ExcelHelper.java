package com.inventario_ms.Common;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
    public static CellStyle createHeaderStyle(XSSFWorkbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setBold(true);
        cellStyle.setFont(cellFont);
        return cellStyle;
    }
    public static void createHeader(String[] column, Row headerRow) {
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
    public static void autoSizeColumns(XSSFSheet sheet, int numberOfColumns) {
        for (int i = 0; i < numberOfColumns; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}
