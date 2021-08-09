package com.sdet.automationFramework.utilities.excelManager;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Excel Parser Main
 * @author SaRa
 * @lastModifiedBy SaRa
 * @lastModifiedDate 20210630
 */
public class ExcelParserMain {

    /**
     * Read a workbook from the Excel file
     * @param targetName File name
     * @return The workbook
     * @throws IOException
     * @throws InvalidFormatException
     * @author SaRa
     * @lastModifiedBy SaRa
     * @lastModifiedDate 20210630
     */
    public static Workbook getWorkbook(String targetName) throws IOException, InvalidFormatException {
        File excelFile = new File(targetName + ".xlsx");
        FileInputStream inp = new FileInputStream( excelFile );
        Workbook workbook = WorkbookFactory.create( inp );
        return workbook;
    }

    /**
     * Construct a json object using the sheet list from the workbook
     * @param workbook
     * @param sheetList Sheet names in the workbook
     * @return Constructed json object
     * @author SaRa
     * @lastModifiedBy SaRa
     * @lastModifiedDate 20210630
     */
    public static JSONObject constructJsonObject(Workbook workbook, String[] sheetList) {
        // Start constructing JSON.
        JSONObject json = new JSONObject();

        // Create JSON
        for (String sheetName : sheetList) {
            JSONArray rows = ExcelParser.parseSheet(workbook, sheetName);
            json.put(sheetName, rows);
        }

        return  json;
    }

    /**
     * Construct a json array using the sheet list from the workbook
     * @param workbook
     * @param sheetList
     * @return
     * @author SaRa
     * @lastModifiedBy SaRa
     * @lastModifiedDate 20210630
     */
    public static JSONArray constructJsonArray(Workbook workbook, String[] sheetList) {
        JSONArray json = new JSONArray();

        // Create JSON
        for (String sheetName : sheetList) {
            JSONArray rows = ExcelParser.parseSheet(workbook, sheetName);
            for (int i=0; i < rows.length(); i++) {
                json.put(rows.get(i));
            }
        }

        return json;
    }

    /**
     * Save a string as a json file
     * @param targetName
     * @param jsonText
     * @throws IOException
     * @author SaRa
     * @lastModifiedBy SaRa
     * @lastModifiedDate 20210630
     */
    public static void saveStringToFile(String targetName, String jsonText) throws IOException {
        // Write into file
        Path path = Paths.get(targetName + ".json");
        BufferedWriter writer = Files.newBufferedWriter( path );
        writer.write(jsonText);
        writer.close();
    }

    /**
     * Parse the excel file and save as json
     * @param targetName Excel file name without suffix
     * @param sheetList Target sheet list
     * @param showSheetName Whether show sheet name in result or not
     * @author SaRa
     * @lastModifiedBy SaRa
     * @lastModifiedDate 20210630
     */
    public static void parseExcelFile(String targetName, String[] sheetList, boolean showSheetName) {
        try {
            Workbook workbook = getWorkbook(targetName);

            String jsonText;

            if (showSheetName) {
                // Get the JSON text.
                JSONObject json = constructJsonObject(workbook, sheetList);
                jsonText = json.toString();
            } else {
                // Get the JSON text.
                JSONArray json = constructJsonArray(workbook, sheetList);
                jsonText = json.toString();
            }

            saveStringToFile(targetName, jsonText);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main test function
     * @param args
     * @throws Exception
     * @author SaRa
     * @lastModifiedBy SaRa
     * @lastModifiedDate 20210630
     */
    public static void main(String[] args) throws Exception{
        String[] configs = args;
        if (args.length == 1) {
            if (args[0].contains("-config=")) {
                String configFile = args[0].replace("-config=", "");
                String content = new Scanner(new File(configFile)).useDelimiter("\\Z").next();
                JSONObject config = new JSONObject(content);

                configs = new String[]{
                        config.getString("target"),
                        config.getString("sheet"),
                        config.getString("show")
                };
            } else {
                throw new IllegalArgumentException("Expected the config filename in the first arguments, please use -config=xxx.json");
            }
        }

        if (configs.length < 2)
            throw new IllegalArgumentException("Expected at least 2 arguments representing Filename, Sheetnames(Divided by space, surrounded by \").");

        if (configs.length > 3)
            throw new IllegalArgumentException("Expected at most 3 arguments representing Filename, Sheetnames(Divided by space, surrounded by \") and a boolean for show sheet names in result or not.");

        String targetName = configs[0];

        if (targetName.equalsIgnoreCase("Validate")) {

        } else {
            if (!targetName.endsWith("xlsx"))
                throw new IllegalArgumentException("The first argument should be an excel(xlsx) file name.");

            // Cut the .xlsx suffix
            targetName = targetName.substring(0, targetName.length()-5);

            // Split sheet names
            String[] sheetList = configs[1].split(" ");

            // Detect show sheet name option
            boolean showSheetName = (configs.length == 3) ? Boolean.parseBoolean(args[2]) : false;

            parseExcelFile(targetName, sheetList, showSheetName);
        }
    }

}
