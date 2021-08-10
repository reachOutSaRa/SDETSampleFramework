package com.sdet.automationFramework.utilities.excelManager;

import com.sdet.automationFramework.utilities.JSONDataManager;
import io.qameta.allure.Allure;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Excel Data Manager
 *
 * @author sraja112
 * @lastModifiedBy sraja112
 * @lastModifiedDate 20210630
 */
public class ExcelDataManager {

    private static final Logger log = LoggerFactory.getLogger(JSONDataManager.class);
    public String path;
    public FileInputStream fis = null;
    public FileOutputStream fileOut = null;
    private XSSFWorkbook workBook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;

    public ExcelDataManager(String path) {

        this.path = path;
        try {
            fis = new FileInputStream(path);
            workBook = new XSSFWorkbook(fis);
            sheet = workBook.getSheetAt(0);
            fis.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * To run this on stand alone
     *
     * @param arg
     * @throws IOException
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public static void main(String arg[]) throws IOException {


        ExcelDataManager datatable = null;
        datatable = new ExcelDataManager("C:\\CM3.0\\app\\test\\Framework\\AutomationBvt\\src\\config\\xlfiles\\Controller.xlsx");
        for (int col = 0; col < datatable.getColumnCount("TC5"); col++) {
            System.out.println(datatable.getCellData("TC5", col, 1));
        }
    }

    /**
     * Returns the row count in a sheet
     *
     * @param sheetName
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public int getRowCount(String sheetName) {
        int index = workBook.getSheetIndex(sheetName);
        if (index == -1)
            return 0;
        else {
            sheet = workBook.getSheetAt(index);
            int number = sheet.getLastRowNum() + 1;
            return number;
        }

    }

    /**
     * Returns the data from a cell
     *
     * @param sheetName
     * @param colName
     * @param rowNum
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public String getCellData(String sheetName, String colName, int rowNum) {
        try {
            if (rowNum <= 0)
                return "";
            int index = workBook.getSheetIndex(sheetName);
            int col_Num = -1;
            if (index == -1)
                return "";

            sheet = workBook.getSheetAt(index);
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                //System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
            if (col_Num == -1)
                return "";

            sheet = workBook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(col_Num);

            if (cell == null)
                return "";

            if (cell.getCellType() == Cell.CELL_TYPE_STRING)
                return cell.getStringCellValue();
            else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {

                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText =
                            (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
                            cal.get(Calendar.MONTH) + 1 + "/" +
                            cellText;


                }


                return cellText;
            } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());

        } catch (Exception e) {

            e.printStackTrace();
            return "row " + rowNum + " or column " + colName + " does not exist in xls";
        }
    }

    /**
     * Returns the data from a cell
     *
     * @param sheetName
     * @param colNum
     * @param rowNum
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public String getCellData(String sheetName, int colNum, int rowNum) {
        try {
            if (rowNum <= 0)
                return "";

            int index = workBook.getSheetIndex(sheetName);

            if (index == -1)
                return "";


            sheet = workBook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(colNum);
            if (cell == null)
                return "";

            if (cell.getCellType() == Cell.CELL_TYPE_STRING)
                return cell.getStringCellValue();
            else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText =
                            (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.MONTH) + 1 + "/" +
                            cal.get(Calendar.DAY_OF_MONTH) + "/" +
                            cellText;


                }


                return cellText;
            } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        } catch (Exception e) {

            e.printStackTrace();
            return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
        }
    }

    /**
     * Returns true if data is set successfully else false
     *
     * @param sheetName
     * @param colName
     * @param rowNum
     * @param data
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        try {
            fis = new FileInputStream(path);
            workBook = new XSSFWorkbook(fis);

            if (rowNum <= 0)
                return false;

            int index = workBook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;


            sheet = workBook.getSheetAt(index);


            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                //System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName))
                    colNum = i;
            }
            if (colNum == -1)
                return false;

            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);


            cell.setCellValue(data);

            fileOut = new FileOutputStream(path);

            workBook.write(fileOut);

            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Returns true if data is set successfully else false
     *
     * @param sheetName
     * @param colName
     * @param rowNum
     * @param data
     * @param url
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {

        try {
            fis = new FileInputStream(path);
            workBook = new XSSFWorkbook(fis);

            if (rowNum <= 0)
                return false;

            int index = workBook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;


            sheet = workBook.getSheetAt(index);

            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {

                if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
                    colNum = i;
            }

            if (colNum == -1)
                return false;
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            cell.setCellValue(data);
            XSSFCreationHelper createHelper = workBook.getCreationHelper();

            //cell style for hyperlinks

            CellStyle hlink_style = workBook.createCellStyle();
            XSSFFont hlink_font = workBook.createFont();
            hlink_font.setUnderline(XSSFFont.U_SINGLE);
            hlink_font.setColor(IndexedColors.BLUE.getIndex());
            hlink_style.setFont(hlink_font);
            //hlink_style.setWrapText(true);

            XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
            link.setAddress(url);
            cell.setHyperlink(link);
            cell.setCellStyle(hlink_style);

            fileOut = new FileOutputStream(path);
            workBook.write(fileOut);

            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Returns true if sheet is created successfully else false
     *
     * @param sheetname
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public boolean addSheet(String sheetname) {

        FileOutputStream fileOut;
        try {
            workBook.createSheet(sheetname);
            fileOut = new FileOutputStream(path);
            workBook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Returns true if sheet is removed successfully else false if sheet does not exist
     *
     * @param sheetName
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public boolean removeSheet(String sheetName) {
        int index = workBook.getSheetIndex(sheetName);
        if (index == -1)
            return false;

        FileOutputStream fileOut;
        try {
            workBook.removeSheetAt(index);
            fileOut = new FileOutputStream(path);
            workBook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Returns true if column is created successfully
     *
     * @param sheetName
     * @param colName
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public boolean addColumn(String sheetName, String colName) {


        try {
            fis = new FileInputStream(path);
            workBook = new XSSFWorkbook(fis);
            int index = workBook.getSheetIndex(sheetName);
            if (index == -1)
                return false;

            XSSFCellStyle style = workBook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            sheet = workBook.getSheetAt(index);

            row = sheet.getRow(0);
            if (row == null)
                row = sheet.createRow(0);


            if (row.getLastCellNum() == -1)
                cell = row.createCell(0);
            else
                cell = row.createCell(row.getLastCellNum());

            cell.setCellValue(colName);
            cell.setCellStyle(style);

            fileOut = new FileOutputStream(path);
            workBook.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;


    }

    /**
     * Removes a column and all the contents
     *
     * @param sheetName
     * @param colNum
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public boolean removeColumn(String sheetName, int colNum) {
        try {
            if (!isSheetExist(sheetName))
                return false;
            fis = new FileInputStream(path);
            workBook = new XSSFWorkbook(fis);
            sheet = workBook.getSheet(sheetName);
            XSSFCellStyle style = workBook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            XSSFCreationHelper createHelper = workBook.getCreationHelper();
            style.setFillPattern(HSSFCellStyle.NO_FILL);


            for (int i = 0; i < getRowCount(sheetName); i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    cell = row.getCell(colNum);
                    if (cell != null) {
                        cell.setCellStyle(style);
                        row.removeCell(cell);
                    }
                }
            }
            fileOut = new FileOutputStream(path);
            workBook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * Find whether sheets exists
     *
     * @param sheetName
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public boolean isSheetExist(String sheetName) {
        int index = workBook.getSheetIndex(sheetName);
        if (index == -1) {
            index = workBook.getSheetIndex(sheetName.toUpperCase());
            if (index == -1)
                return false;
            else
                return true;
        } else
            return true;
    }

    /**
     * Returns number of columns in a sheet
     *
     * @param sheetName
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public int getColumnCount(String sheetName) {
        // check if sheet exists
        if (!isSheetExist(sheetName))
            return -1;

        sheet = workBook.getSheet(sheetName);
        row = sheet.getRow(0);

        if (row == null)
            return -1;

        return row.getLastCellNum();

    }

    /**
     * Add hyperlink
     *
     * @param sheetName
     * @param screenShotColName
     * @param testCaseName
     * @param index
     * @param url
     * @param message
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url, String message) {


        url = url.replace('\\', '/');
        if (!isSheetExist(sheetName))
            return false;

        sheet = workBook.getSheet(sheetName);

        for (int i = 2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {

                setCellData(sheetName, screenShotColName, i + index, message, url);
                break;
            }
        }


        return true;
    }

    /**
     * Return Row number
     *
     * @param sheetName
     * @param colName
     * @param cellValue
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public int getCellRowNum(String sheetName, String colName, String cellValue) {

        for (int i = 2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
                return i;
            }
        }
        return -1;

    }

    /**
     * Function to Convert the Excel Row into JSONObject
     *
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public Object writeToJSONFromExcelFile() {
        DataFormatter dataFormatter = new DataFormatter();
        JSONObject workbookJson = new JSONObject();
        JSONArray sheetJson = new JSONArray();
        JSONObject rowJson = new JSONObject();
        String pathWithoutName = getPathWithoutFileName(path);

        try {

            FormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workBook);

            for (int noOfSheeet = 0; noOfSheeet < workBook.getNumberOfSheets(); noOfSheeet++) {
                Sheet sheet = workBook.getSheetAt(noOfSheeet);
                sheetJson = new JSONArray();
                int lastRowNum = sheet.getLastRowNum();
                int lastColumnNum = sheet.getRow(0).getLastCellNum();
                Row firstRowAsKeys = sheet.getRow(0); // first row as a json keys

                for (int i = 1; i <= lastRowNum; i++) {
                    rowJson = new JSONObject();
                    Row row = sheet.getRow(i);

                    if (row != null) {
                        for (int j = 0; j < lastColumnNum; j++) {
                            formulaEvaluator.evaluate(row.getCell(j));
                            rowJson.put(firstRowAsKeys.getCell(j).getStringCellValue(),
                                    dataFormatter.formatCellValue(row.getCell(j), formulaEvaluator));
                        }
                        sheetJson.add(rowJson);

                        //Write into the file
                        try (FileWriter file = new FileWriter(pathWithoutName + "\\" + sheet.getSheetName() + ".json")) {
                            file.write(sheetJson.toString());
                        }
                        //write sheetJson to new json with sheet as file name
                    }
                }
                workbookJson.put(sheet.getSheetName(), sheetJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Allure.addAttachment("JSON Files", "Created Successfully");
        return workbookJson;
    }

    /**
     * Function to get path without file name
     *
     * @param path
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public String getPathWithoutFileName(String path) {
        String regEx4Win = "\\\\(?=[^\\\\]+$)";
        String[] tokens = path.split(regEx4Win);
        String pathWithoutName = null;
        if (tokens.length > 0)
            pathWithoutName = tokens[0]; // path -> d:\folder1\subfolder11

        return pathWithoutName;
    }

    /**
     * Function to extract the file nae from path
     *
     * @param fullPathFile
     * @return
     * @author sraja112
     * @lastModifiedBy sraja112
     * @lastModifiedDate 20210630
     */
    public String extractFileName(String fullPathFile) {
        try {
            Pattern regex = Pattern.compile("([^\\\\/:*?\"<>|\r\n]+$)");
            Matcher regexMatcher = regex.matcher(fullPathFile);
            if (regexMatcher.find()) {
                return regexMatcher.group(1);
            }
        } catch (PatternSyntaxException ex) {
            //LOG.info("extractFileName::pattern problem <"+fullPathFile+">",ex);
        }
        return fullPathFile;
    }

}
