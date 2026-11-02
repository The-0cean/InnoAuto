package org.example.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.preemptive;

public class RestApiBuilder {

    private static final String LOGIN = "admin";
    private static final String PASSWORD = "secret123";

    // Заготовка без логина — для GET /goods/list, он открыт всем
    public static RequestSpecification getBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(Urls.BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
    }

    // Заготовка с логином — для всех остальных эндпоинтов
    public static RequestSpecification getAuthBuilder() {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBuilder())
                .setAuth(preemptive().basic(LOGIN, PASSWORD))
                .build();
    }
}