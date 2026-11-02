package org.example.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@Tag("api")
public class GoodsAddTest extends BaseApiTest {


    @Test
    public void addValidGoodReturns200() {
        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .body(Map.of("name", GoodsApi.uniqueName("Bread"), "price", 45.0))
                .post(Urls.GOODS_ADD);

        assertThat(response.statusCode())
                .as("valid good must be created with 200")
                .isEqualTo(200);
        assertThat(response.jsonPath().getString("message"))
                .as("server must report success")
                .isEqualTo("success");
    }

    // 400: пустое имя
    @Test
    public void addGoodWithEmptyNameReturns400() {
        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .body(Map.of("name", "", "price", 45.0))
                .post(Urls.GOODS_ADD);

        assertThat(response.statusCode())
                .as("empty name must give 400")
                .isEqualTo(400);
        assertThat(response.jsonPath().getString("name"))
                .as("error text for name")
                .isEqualTo("Name cannot be empty");
    }

    // 400: отрицательная цена
    @Test
    public void addGoodWithNegativePriceReturns400() {
        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .body(Map.of("name", GoodsApi.uniqueName("Oil"), "price", -5.0))
                .post(Urls.GOODS_ADD);

        assertThat(response.statusCode())
                .as("negative price must give 400")
                .isEqualTo(400);
        assertThat(response.jsonPath().getString("price"))
                .as("error text for price")
                .isEqualTo("Price can not be less, than 0");
    }

    // 400: имя уже занято
    @Test
    public void addGoodWithDuplicateNameReturns400() {
        String name = GoodsApi.uniqueName("Cheese");
        GoodsApi.addGood(name, 300.0);

        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .body(Map.of("name", name, "price", 300.0))
                .post(Urls.GOODS_ADD);

        assertThat(response.statusCode())
                .as("duplicate name must give 400")
                .isEqualTo(400);
        assertThat(response.jsonPath().getString("message"))
                .as("error text for duplicate")
                .contains("already exists");
    }
}