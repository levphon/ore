package org.oreframework.commons.office.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


/** 
 * 操作Excel的公共类,这个支持.xls,xlsx
 * @author  huangzz
 * @version  [1.0.0, 2015-9-22]
 */
@Deprecated
public class ExcelUtils {


    // 模板文件目录
    private static String FILECLASSPATH = "/attachment/";

    // 工作薄，也就是一个excle文件
    private Workbook wb = null;

    // 一个excle文件可以有多个sheet
    private Sheet sheet = null;

    // 代表了表的第一行，也就是列名
    private Row row = null;

    // 一个excel有多个sheet，这是其中一个
    private int sheetNum = 0; // 第sheetnum个工作表

    // 一个sheet中可以有多行，这里应该是给行数的定义
    private int rowNum = 0;

    // 文件输入流
    private InputStream fis = null;

    // 指定文件
    private File file = null;

    public ExcelUtils(File file) {
        this.file = file;
    }

    /**
     * 读取excel文件获得HSSFWorkbook对象
     */
    public void open() throws Exception {
        fis = new FileInputStream(file);
        wb = WorkbookFactory.create(fis);
        fis.close();
    }

    public void open(InputStream is) throws Exception {
        fis = is;
        wb = WorkbookFactory.create(fis);
        fis.close();
    }

    /**
     * 返回sheet表数目
     * @return int
     */
    public int getSheetCount() {
        int sheetCount = -1;
        sheetCount = wb.getNumberOfSheets();
        return sheetCount;
    }

    /**
     * sheetNum下的记录行数
     * @return int
     */
    public int getRowCount() {
        if (wb == null)
            System.out.println("=============>WorkBook为空");
        Sheet sheet = wb.getSheetAt(this.sheetNum);
        int rowCount = -1;
        rowCount = sheet.getLastRowNum();
        return rowCount;
    }

    /**
     * 读取指定sheetNum的rowCount
     * @param sheetNum
     * @return int
     */
    public int getRowCount(int sheetNum) {
        Sheet sheet = wb.getSheetAt(sheetNum);
        int rowCount = -1;
        rowCount = sheet.getLastRowNum();
        return rowCount;
    }

    /**
     * 得到指定行的内容
     * @param lineNum
     * @return String[]
     */
    public String[] readExcelLine(int lineNum) {
        return readExcelLine(this.sheetNum, lineNum);
    }

    /**
     * 指定工作表和行数的内容
     * @param sheetNum
     * @param lineNum
     * @return String[]
     */
    public String[] readExcelLine(int sheetNum, int lineNum) {
        if (sheetNum < 0 || lineNum < 0)
            return null;
        String[] strExcelLine = null;
        try {
            sheet = wb.getSheetAt(sheetNum);
            row = sheet.getRow(lineNum);
            if (row == null) {
                return null;
            }
            int cellCount = row.getLastCellNum();
            strExcelLine = new String[cellCount + 5];
            for (int i = 0; i <= cellCount; i++) {
                strExcelLine[i] = readStringExcelCell(lineNum, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strExcelLine;
    }

    /**
     * 获取指定工作表的名称
     */
    public String getSheetName(int sheetNum) {
        sheet = wb.getSheetAt(sheetNum);
        return sheet.getSheetName();
    }

    /**
     * 读取指定列的内容
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int cellNum) {
        return readStringExcelCell(this.rowNum, cellNum);
    }

    /**
     * 指定行和列编号的内容
     * @param rowNum
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int rowNum, int cellNum) {
        return readStringExcelCell(this.sheetNum, rowNum, cellNum);
    }

    /**
     * 指定工作表、行、列下的内容
     * @param sheetNum
     * @param rowNum
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int sheetNum, int rowNum, int cellNum) {
        if (sheetNum < 0 || rowNum < 0)
            return "";
        String strExcelCell = "";
        try {
            sheet = wb.getSheetAt(sheetNum);
            row = sheet.getRow(rowNum);

            if (row.getCell((short) cellNum) != null) { // add this condition
                // judge
                switch (row.getCell((short) cellNum).getCellType()) {
                case Cell.CELL_TYPE_FORMULA:
                    strExcelCell = String.valueOf(row.getCell((short) cellNum).getCellFormula());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(row.getCell((short) cellNum))) {
                        //strExcelCell = DateFormatConvert.getDateString(row.getCell((short) cellNum).getDateCellValue());
                    } else {
                        BigDecimal bd = new BigDecimal(row.getCell((short) cellNum).getNumericCellValue());
                        strExcelCell = bd.toPlainString();
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    strExcelCell = row.getCell((short) cellNum).getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    strExcelCell = "";
                    break;
                default:
                    strExcelCell = "";
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strExcelCell;
    }

    public static void main(String args[]) {
        File file = new File("D:\\testRead.xls");
        ExcelUtils readExcel = new ExcelUtils(file);
        try {
            readExcel.open();
        } catch (Exception e) {
        }
        readExcel.setSheetNum(0); // 设置读取索引为0的工作表
        // 总行数
        int count = readExcel.getRowCount();
        for (int i = 0; i <= count; i++) {
            String[] rows = readExcel.readExcelLine(i);
            for (int j = 0; j < rows.length; j++) {
                System.out.print(rows[j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setSheetNum(int sheetNum) {
        this.sheetNum = sheetNum;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
