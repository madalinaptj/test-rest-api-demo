package com.demo.testapi.it;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.testapi.it.util.ApplicationContextConfig;
import com.demo.testapi.it.util.ResourceName;
import com.demo.testapi.it.util.SetupUtil;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(classes = ApplicationContextConfig.class)
public class DemoIT {

    @DisplayName("Test POST status code 201")
    @Test
    public void firstTest() {
        RestAssured.baseURI = "http://hapi.fhir.org/baseR4/";
        RestAssured.basePath = "Patient";
        String body = SetupUtil.readTestDataFile(ResourceName.PATIENT, "Simple");
        given().contentType("application/fhir+json").body(body).when().post().then().statusCode(201);
    }

    @DisplayName("Test POST status code 201 alternative")
    @Test
    public void secondTest() {
        RestAssured.baseURI = "http://hapi.fhir.org/baseR4/";
        RestAssured.basePath = "Patient";
        RequestSpecBuilder builder = SetupUtil.createRequestSpecBuilder(ResourceName.PATIENT, "Simple");
        RequestSpecification spec = builder.build();
        given().spec(spec).when().post().then().statusCode(201);

    }

}