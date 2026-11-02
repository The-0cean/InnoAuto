package org.example.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@Tag("api")
public class GoodsListTest extends BaseApiTest {


    @Test
    public void listReturns200WithCreatedGood() {
        String name = GoodsApi.uniqueName("Milk");
        GoodsApi.addGood(name, 80.0);

        Response response = given()
                .spec(RestApiBuilder.getBuilder())
                .get(Urls.GOODS_LIST);

        assertThat(response.statusCode())
                .as("GET /goods/list must return 200")
                .isEqualTo(200);
        assertThat(response.jsonPath().getList("goods.name", String.class))
                .as("list must contain %s", name)
                .contains(name);
    }

    // 200: постраничный вывод — size=2 отдаёт ровно два товара из трёх
    @Test
    public void listReturns200WithPageSize() {
        GoodsApi.addGood(GoodsApi.uniqueName("A"), 1.0);
        GoodsApi.addGood(GoodsApi.uniqueName("B"), 2.0);
        GoodsApi.addGood(GoodsApi.uniqueName("C"), 3.0);

        Response response = given()
                .spec(RestApiBuilder.getBuilder())
                .queryParam("size", 2)
                .get(Urls.GOODS_LIST);

        assertThat(response.statusCode())
                .as("GET /goods/list?size=2 must return 200")
                .isEqualTo(200);
        assertThat(response.jsonPath().getList("goods"))
                .as("page of size 2 must contain exactly 2 goods")
                .hasSize(2);
    }
}