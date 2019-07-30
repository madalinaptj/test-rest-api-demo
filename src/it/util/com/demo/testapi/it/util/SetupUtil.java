package com.demo.testapi.it.util;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.builder.RequestSpecBuilder;

public class SetupUtil {

    private SetupUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final String PATH_DELIM = "/";

    public static RequestSpecBuilder createRequestSpecBuilder(ResourceName resource, String fileSuffix) {
        String requestBody = "";
        RequestSpecBuilder builder = new RequestSpecBuilder();

        requestBody = SetupUtil.readTestDataFile(ResourceName.PATIENT, "Simple");
        builder.setBody(requestBody);

        builder.setContentType("application/fhir+json");

        return builder;
    }

    public static String readTestDataFile(ResourceName resource, String fileSuffix) {
        String path = buildFilePath(resource, fileSuffix);

        String fileContent = "";
        try {
            fileContent = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            fail("Test data not found");
        }
        return fileContent;
    }

    private static String buildFilePath(ResourceName resource, String fileSuffix) {
        String resourceName = resource.getValue();

        return "src/it/resources/R4/" + resource.getValue() + PATH_DELIM + resourceName + fileSuffix + ".json";
    }

}
