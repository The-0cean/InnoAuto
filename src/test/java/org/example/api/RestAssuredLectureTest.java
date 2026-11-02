package org.example.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

@Tag("api")
public class RestAssuredLectureTest {

    @BeforeEach
    public void clearBefore() {
        GoodsApi.deleteAllGoods();
    }

    @AfterEach
    public void clearAfter() {
        GoodsApi.deleteAllGoods();
    }

    @Test
    public void listIsEmptyGivenWhenThen() {
        given()
                .baseUri(Urls.BASE_URI)
                .when()
                .get(Urls.GOODS_LIST)
                .then()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    public void listIsEmptyRequestSpecification() {
        RequestSpecification spec = RestApiBuilder.getBuilder();

        given()
                .spec(spec)
                .when()
                .get(Urls.GOODS_LIST)
                .then()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    public void addedGoodIsInListBuiltInChecks() {
        String name = GoodsApi.uniqueName("Apple");

        int id = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .body(Map.of("name", name, "price", 10.5))
                .when()
                .post(Urls.GOODS_ADD)
                .then()
                .statusCode(200)
                .body("message", equalTo("success"))
                .extract().jsonPath().getInt("data.id");

        given()
                .spec(RestApiBuilder.getBuilder())
                .when()
                .get(Urls.GOODS_LIST)
                .then()
                .statusCode(200)
                .body("goods.id", hasItem(id))
                .body("goods.name", hasItem(name));
    }

    // 1.4 — то же самое, но проверки через AssertJ
    @Test
    public void addedGoodIsInListAssertJ() {
        String name = GoodsApi.uniqueName("Pear");

        Response addResponse = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .body(Map.of("name", name, "price", 20.0))
                .post(Urls.GOODS_ADD);

        assertThat(addResponse.statusCode())
                .as("POST /goods/add must return 200")
                .isEqualTo(200);

        int id = addResponse.jsonPath().getInt("data.id");

        Response listResponse = given()
                .spec(RestApiBuilder.getBuilder())
                .get(Urls.GOODS_LIST);

        assertThat(listResponse.statusCode())
                .as("GET /goods/list must return 200")
                .isEqualTo(200);

        assertThat(listResponse.jsonPath().getList("goods.id", Integer.class))
                .as("goods list must contain id %d", id)
                .contains(id);

        assertThat(listResponse.jsonPath().getList("goods.name", String.class))
                .as("goods list must contain name %s", name)
                .contains(name);
    }
}