package java.com.sdet.automationFramework.unitTests.xlsx2json;

import com.axp.automationFramework.utilities.excelManager.ExcelParserMain;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Feliciano on 6/2/2016.
 */
public class ExcelParserMainTest {

    /**
     * Validate two json file.  If it isn't it throws an {@link AssertionError}.
     * @param expectedFileName Expected JSON file
     * @param targetFileName File to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void validateJson(String expectedFileName, String targetFileName, boolean strict) {
        try {
            System.out.println("Checking " + targetFileName + " ... ");
            Path path = Paths.get(targetFileName);
            BufferedReader reader = Files.newBufferedReader(path);
            String jsonText = reader.readLine();

            path = Paths.get(expectedFileName);
            reader = Files.newBufferedReader(path);
            String expected = reader.readLine();

            JSONAssert.assertEquals(expected, jsonText, false);

            System.out.println("Passed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void main() throws Exception {
        ExcelParserMain.main(new String[]{"src/main/resources/testDataSheet/ccc.xlsx", "data", "false"});

        //validateJson("src/test/java/com/axp//unitTests/testcasestest3.expected.json", "src/test/java/com/axp//unitTests/testcasestest.json", false);
    }

    @Test
    public void mainMultisheets() throws Exception {
        ExcelParserMain.main(new String[]{"src/test/java/com/axp//unitTests/testcases/test.xlsx", "weaponStuffs skillStuffs essenceStuffs", "true"});

        validateJson("src/test/java/com/axp//unitTests/testcasestest4.expected.json", "src/test/java/com/axp//unitTests/testcasestest.json", false);
    }

    @Test
    public void mainHideSheetName() throws Exception {
        ExcelParserMain.main(new String[]{"src/test/java/com/axp//unitTests/testcases/test.xlsx", "map", "false"});

        validateJson("src/test/java/com/axp//unitTests/testcasestest5.expected.json", "src/test/java/com/axp//unitTests/testcasestest.json", false);
    }

    @Test
    public void mainMultiSheetsAndHideSheetName() throws Exception {
        ExcelParserMain.main(new String[]{"src/test/java/com/axp//unitTests/testcases/test.xlsx", "monsters", "false"});

        validateJson("src/test/java/com/axp//unitTests/testcasestest6.expected.json", "src/test/java/com/axp//unitTests/testcasestest.json", false);
    }

    @Test(expected=IllegalArgumentException.class)
    public void mainWrongTargetName() throws Exception {
        ExcelParserMain.main(new String[]{"test.xls", "", ""});
    }

    @Test(expected=IllegalArgumentException.class)
    public void mainWrongNumberofArguments() throws Exception {
        ExcelParserMain.main(new String[]{"test.xls"});
    }

}