package org.example.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

// DELETE /goods/{id} — по Swagger: 200 и 404
@Tag("api")
public class GoodsDeleteTest extends BaseApiTest {

    // 200: товар удаляется и пропадает из списка
    @Test
    public void deleteExistingGoodReturns200() {
        int id = GoodsApi.addGood(GoodsApi.uniqueName("Butter"), 120.0);

        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .pathParam("id", id)
                .delete(Urls.GOODS_BY_ID);

        assertThat(response.statusCode())
                .as("delete of existing good must return 200")
                .isEqualTo(200);
        assertThat(response.asString())
                .as("server message")
                .contains("deleted successfully");

        List<Integer> idsLeft = given()
                .spec(RestApiBuilder.getBuilder())
                .get(Urls.GOODS_LIST)
                .jsonPath()
                .getList("goods.id", Integer.class);
        assertThat(idsLeft)
                .as("deleted good %d must disappear from list", id)
                .doesNotContain(id);
    }

    // 404: такого товара нет
    @Test
    public void deleteNonExistentGoodReturns404() {
        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .pathParam("id", NON_EXISTENT_ID)
                .delete(Urls.GOODS_BY_ID);

        assertThat(response.statusCode())
                .as("delete of non-existent good must return 404")
                .isEqualTo(404);
        assertThat(response.asString())
                .as("error text")
                .contains("is not found");
    }
}