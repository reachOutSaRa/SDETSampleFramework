package com.sdet.automationFramework.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONDataManager {

    private static final Logger log = LoggerFactory.getLogger(JSONDataManager.class);
    private static HashMap<String, List> testData;

    public JSONDataManager() {
    }

    public static <T> HashMap<String, List> getInstance(Class<T> testDataClass, String resourcePath)
            throws IOException {

        Class var2;
        if (testData == null) {
            var2 = JSONDataManager.class;
            synchronized (JSONDataManager.class) {
                if (testData == null) {
                    testData = new HashMap();
                    testData.put(
                            testDataClass.getCanonicalName()
                                    + "_"
                                    + resourcePath,
                            readTestData(testDataClass, resourcePath));
                }
            }
        } else if (!testData.containsKey(testDataClass.getCanonicalName() + "_" + resourcePath)) {
            var2 = JSONDataManager.class;
            synchronized (JSONDataManager.class) {
                if (!testData.containsKey(testDataClass.getCanonicalName() + "_" + resourcePath)) {
                    testData.put(testDataClass.getCanonicalName()
                            + "_"
                            + resourcePath,
                            readTestData(testDataClass, resourcePath));
                }
            }
        }

        return testData;
    }

    public static <T> List readTestData(Class<T> testDataClass, String resourcePath) throws IOException {

        log.debug("Reading {} to create objects for {}", resourcePath, testDataClass.getCanonicalName());
        InputStreamReader resourceStream = findAppInstanceSpecificResource(resourcePath);
        BufferedReader appJson = new BufferedReader(resourceStream);
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, testDataClass);
        return (List) objectMapper.readValue(appJson, javaType);
    }

    public static InputStreamReader findAppInstanceSpecificResource(String resourcePath) throws FileNotFoundException {

        ClassLoader classLoader = JSONDataManager.class.getClassLoader();
        Pattern patternForResourceNameWithAppInstanceName = Pattern.compile("(.*)[_\\-](.*)\\.json");
        Matcher matcher = patternForResourceNameWithAppInstanceName.matcher(resourcePath);
        boolean hasInstanceNameInResourcePath = matcher.matches();
        InputStream resourceAsStream;
        String resourcePathWithOutAppInstanceName;

        if (hasInstanceNameInResourcePath) {
            log.trace("{} has App instance name", resourcePath);
            resourceAsStream = classLoader.getResourceAsStream(resourcePath);
            if (Objects.nonNull(resourceAsStream)) {
                log.debug("Reading from resource path as Provided");
                return new InputStreamReader(resourceAsStream);
            } else {
                resourcePathWithOutAppInstanceName = MessageFormat.format("{0}.json", matcher.group(1));
                InputStream resourceWithOutAppInstanceName =
                        classLoader.getResourceAsStream(resourcePathWithOutAppInstanceName);
                if (Objects.nonNull(resourcePathWithOutAppInstanceName)) {
                    log.error("Reading from resource path without App Instance Name - {}",
                            resourcePathWithOutAppInstanceName);
                    return new InputStreamReader(resourceWithOutAppInstanceName);
                } else {
                    String message = MessageFormat.format(
                            "Failed to find file with names {0},{1}",
                            resourcePath,
                            resourcePathWithOutAppInstanceName);
                    throw new FileNotFoundException(message);
                }
            }
        } else {
            log.debug("{} does not have app instance name", resourcePath);
            log.debug("Reading from resource path as provided");
            resourceAsStream = classLoader.getResourceAsStream(resourcePath);
            if (Objects.nonNull(resourceAsStream)) {
                return new InputStreamReader(resourceAsStream);
            } else {
                log.error("Reading from resource path without App Instance Name - {}", resourceAsStream);
                resourceAsStream = classLoader.getResourceAsStream(resourcePath);
                if(Objects.nonNull(resourceAsStream)){
                    return new InputStreamReader(resourceAsStream);
                } else{
                    resourcePathWithOutAppInstanceName = MessageFormat.format(
                            "Failed to find file with name {0}",
                            resourcePath );
                    throw new FileNotFoundException(resourcePathWithOutAppInstanceName);
                }
            }
        }
    }

    public static <T> JSONData getInstance(Class<T> testDataClass, String resourcePath, String dataDescriptionInput)
            throws IOException {
        List<JSONData> testDatumInterfaces =
                (List)getInstance(testDataClass, resourcePath)
                        .get(testDataClass.getCanonicalName() + "_" + resourcePath);

        testDatumInterfaces.stream().forEach((testData1) -> {
            log.trace(testData1.toString());
        });

        return (JSONData) testDatumInterfaces.stream().filter((dataDescription) -> {
            return dataDescription.getDataDescription().equalsIgnoreCase(dataDescriptionInput);
        }).findFirst().orElseThrow(() -> {
            String exceptionMessage = MessageFormat.format(
                    "Unable to find the data with description {0}. Verify the description",
                    dataDescriptionInput);
            return new IOException(exceptionMessage);
        });
    }

    public static <T> JSONData getResource(){
        //resource Code
        return null;
    }

    public static void assignProperties(Object source, Object target)
            throws InvocationTargetException,IllegalAccessException {
        Method[] methods = source.getClass().getMethods();
        Field[] declaredFields = source.getClass().getDeclaredFields();
        Field[] var4 = declaredFields;
        int var5 = declaredFields.length;

    }



}
