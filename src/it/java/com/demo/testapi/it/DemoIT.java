package com.demo.testapi.it;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.testapi.it.util.ApplicationContextConfig;
import com.demo.testapi.it.util.ResourceName;
import com.demo.testapi.it.util.SetupUtil;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(classes = ApplicationContextConfig.class)
public class DemoIT {

    @BeforeAll
    public void setup() {
        setBaseUri();
    }

    private void setBaseUri() {
        RestAssured.baseURI = "http://hapi.fhir.org/baseR4/";
    }

    @BeforeEach
    public void setupEach() {
        RestAssured.basePath = "Patient";
    }

    @DisplayName("Test POST status code 201")
    @Test
    public void firstTest() {
        String body = SetupUtil.readTestDataFile(ResourceName.PATIENT, "Simple");
        given().contentType("application/fhir+json").body(body).when().post().then().statusCode(201).and()
                .contentType(ContentType.JSON);
    }

    @DisplayName("Test POST status code 201 alternative")
    @Test
    public void secondTest() {
        RequestSpecBuilder builder = SetupUtil.createRequestSpecBuilder(ResourceName.PATIENT, "Simple");
        RequestSpecification spec = builder.build();
        given().spec(spec).when().post().then().statusCode(201);

    }

    @DisplayName("Test POST request")
    @Test
    public void postTest() {
        RequestSpecBuilder builder = SetupUtil.createRequestSpecBuilder(ResourceName.PATIENT, "Simple");
        RequestSpecification spec = builder.build();
        Response response = given().spec(spec).when().post();

        assertEquals(201, response.statusCode(), "Status code wrong");
        assertEquals("application/fhir+json;charset=utf-8", response.getContentType(), "ContentType wrong");
        assertNotNull(response.getHeader("Last-Modified"), "Last-Modified header missing");

        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        try {
            Date date = format.parse(response.getHeader("Last-Modified"));
        } catch (ParseException e) {
            fail("Exception: Last-Modified header does not have a valid date time format");
        }
    }

    @DisplayName("Test GET request")
    @ParameterizedTest
    @EnumSource(ResourceName.class)
    public void getTest(ResourceName resource) {
        RestAssured.basePath = resource.getValue();
        RequestSpecBuilder builder = SetupUtil.createRequestSpecBuilder(resource, "Simple");
        RequestSpecification spec = builder.build();
        Response create = given().spec(spec).when().post();

        JsonPath json = new JsonPath(create.body().asString());
        RestAssured.basePath = resource + "/" + json.get("id");

        Response get = given().spec(spec).when().get();

        assertEquals(200, get.statusCode(), "Status code wrong");
        assertEquals("application/fhir+json;charset=utf-8", get.getContentType(), "ContentType wrong");
        assertNotNull(get.getHeader("Last-Modified"), "Last-Modified header missing");

        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        try {
            Date date = format.parse(get.getHeader("Last-Modified"));
        } catch (ParseException e) {
            fail("Exception: Last-Modified header does not have a valid date time format");
        }
    }

}