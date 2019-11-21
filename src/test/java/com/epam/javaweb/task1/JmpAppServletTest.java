package com.epam.javaweb.task1;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class JmpAppServletTest {
    private final String BASIC_URI = "http://localhost:8080/jmp8-12/jmp";

    @Test
    public void putTest() {
        given().contentType(JSON).body("stringToUpdate=first&newString=replacement")
                .when().put(BASIC_URI)
                .then().body("result", is("updated"));
    }

    @Test
    public void deleteTest() {
        given().contentType(JSON).body("stringToDelete=second")
                .when().delete(BASIC_URI)
                .then().body("result", is("deleted"));
    }

}
