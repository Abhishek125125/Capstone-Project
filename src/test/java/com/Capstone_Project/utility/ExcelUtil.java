package com.Capstone_Project.utility;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {
    static Workbook workbook;
    static Sheet sheet;

    public static void setExcelFile(String path, String sheetName) throws Exception {
        FileInputStream fis = new FileInputStream(path);
        workbook = WorkbookFactory.create(fis);
        sheet = workbook.getSheet(sheetName);
    }

    public static int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public static String getCellData(int row, int col) {
        return sheet.getRow(row).getCell(col).getStringCellValue();
    }

    public static Object[][] getTestData() throws Exception {
        int rowCount = getRowCount();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = getCellData(i, j);
            }
        }
        return data;
    }
}
