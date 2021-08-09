package java.com.sdet.automationFramework.unitTests;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public class ExcelToJsonConvertor {
    private Object readExcelFileAsJsonObject_RowWise(String filePath) {
        DataFormatter dataFormatter = new DataFormatter();
        JSONObject workbookJson = new JSONObject();
        JSONArray sheetJson = new JSONArray();
        JSONObject rowJson = new JSONObject();
        String regEx4Win = "\\\\(?=[^\\\\]+$)";
        String[] tokens = filePath.split(regEx4Win);
        String pathWithoutName = null;
        if (tokens.length > 0)
            pathWithoutName = tokens[0]; // path -> d:\folder1\subfolder11
        try {

            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workBook = new XSSFWorkbook(excelFile);
            FormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workBook);

            for (int noOfSheeet =0 ; noOfSheeet < workBook.getNumberOfSheets(); noOfSheeet++) {
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
                            String temp = firstRowAsKeys.getCell(j).getStringCellValue();
                            if(temp.contains(".")) {
                                System.out.println(temp + ": "+row.getCell(j));
                                String[] arraySample = temp.split(".");
                                for(int n = 0 ; n < arraySample.length ; n++){
                                    System.out.println(arraySample[n]);
                                }

                            }
                            System.out.println(firstRowAsKeys.getCell(j).getStringCellValue() + ": "+row.getCell(j));
                            rowJson.put(firstRowAsKeys.getCell(j).getStringCellValue(),
                                    dataFormatter.formatCellValue(row.getCell(j), formulaEvaluator));
                        }
                        sheetJson.add(rowJson);

                        //Write into the file
                        try (FileWriter file = new FileWriter(pathWithoutName + sheet.getSheetName() + ".json"))
                        {
                            file.write(sheetJson.toString());
                            System.out.println(pathWithoutName + "\\" + sheet.getSheetName() + ".json");
                        }
                        //write sheetJson to new json with sheet as file name
                    }
                }
                workbookJson.put(sheet.getSheetName(), sheetJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //write the sheetJson
        return sheetJson;
    }

    public static void main(String arg[]) {

        ExcelToJsonConvertor excelConvertor = new ExcelToJsonConvertor();
        String filePath =
                "C:\\oysterPlusAutomationFramework";

        Object data = excelConvertor.readExcelFileAsJsonObject_RowWise(filePath);
        System.out.println(data);
    }
}
