package com.demo.testapi.it;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.testapi.it.util.ApplicationContextConfig;

import io.restassured.RestAssured;

@SpringBootTest(classes = ApplicationContextConfig.class)
public class DemoIT {

    @DisplayName("Test POST status code 201")
    @Test
    public void firstTest() {
        RestAssured.baseURI = "http://hapi.fhir.org/baseR4/";
        RestAssured.basePath = "Patient/";
        given().contentType("application/fhir+json").when().post().then().statusCode(201);
    }

    @DisplayName("Test POST status code 201")
    @Test
    public void secondTest() {
        fail();
    }
}