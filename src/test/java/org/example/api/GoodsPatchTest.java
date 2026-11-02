package org.example.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

// PATCH /goods/{id} — по Swagger: 200, 400 и 404
@Tag("api")
public class GoodsPatchTest extends BaseApiTest {

    // 200: товар переименовывается
    @Test
    public void patchExistingGoodReturns200() {
        int id = GoodsApi.addGood(GoodsApi.uniqueName("Coffee"), 300.0);
        String newName = GoodsApi.uniqueName("Coffee-new");

        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .pathParam("id", id)
                .body(Map.of("name", newName, "price", 350.0))
                .patch(Urls.GOODS_BY_ID);

        assertThat(response.statusCode())
                .as("patch of existing good must return 200")
                .isEqualTo(200);
        assertThat(response.jsonPath().getString("name"))
                .as("name must be updated")
                .isEqualTo(newName);
    }

    // 400: пустое имя
    @Test
    public void patchWithEmptyNameReturns400() {
        int id = GoodsApi.addGood(GoodsApi.uniqueName("Juice"), 90.0);

        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .pathParam("id", id)
                .body(Map.of("name", "", "price", 90.0))
                .patch(Urls.GOODS_BY_ID);

        assertThat(response.statusCode())
                .as("empty name must give 400")
                .isEqualTo(400);
        assertThat(response.jsonPath().getString("name"))
                .as("error text for name")
                .isEqualTo("Name cannot be empty");
    }

    // 400: имя занято другим товаром
    @Test
    public void patchWithTakenNameReturns400() {
        String takenName = GoodsApi.uniqueName("Sugar");
        GoodsApi.addGood(takenName, 70.0);
        int id = GoodsApi.addGood(GoodsApi.uniqueName("Salt"), 30.0);

        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .pathParam("id", id)
                .body(Map.of("name", takenName, "price", 30.0))
                .patch(Urls.GOODS_BY_ID);

        assertThat(response.statusCode())
                .as("taken name must give 400")
                .isEqualTo(400);
        assertThat(response.asString())
                .as("error text")
                .contains("already exists");
    }

    // 404: такого товара нет
    @Test
    public void patchNonExistentGoodReturns404() {
        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .pathParam("id", NON_EXISTENT_ID)
                .body(Map.of("name", GoodsApi.uniqueName("Ghost"), "price", 1.0))
                .patch(Urls.GOODS_BY_ID);

        assertThat(response.statusCode())
                .as("patch of non-existent good must return 404")
                .isEqualTo(404);
        assertThat(response.asString())
                .as("error text")
                .contains("is not found");
    }
}