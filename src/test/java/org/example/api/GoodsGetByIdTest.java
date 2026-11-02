package org.example.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@Tag("api")
public class GoodsGetByIdTest extends BaseApiTest {

    @Test
    public void getExistingGoodReturns200() {
        String name = GoodsApi.uniqueName("Tea");
        int id = GoodsApi.addGood(name, 150.0);

        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .pathParam("id", id)
                .get(Urls.GOODS_BY_ID);

        assertThat(response.statusCode())
                .as("existing good must return 200")
                .isEqualTo(200);
        assertThat(response.jsonPath().getString("name"))
                .as("name of good %d", id)
                .isEqualTo(name);
        assertThat(response.jsonPath().getDouble("price"))
                .as("price of good %d", id)
                .isEqualTo(150.0);
    }

    // 404: такого товара нет
    @Test
    public void getNonExistentGoodReturns404() {
        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .pathParam("id", NON_EXISTENT_ID)
                .get(Urls.GOODS_BY_ID);

        assertThat(response.statusCode())
                .as("non-existent good must return 404")
                .isEqualTo(404);
        assertThat(response.jsonPath().getString("message"))
                .as("error text")
                .contains("is not found");
    }
}