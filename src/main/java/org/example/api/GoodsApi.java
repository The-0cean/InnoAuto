package org.example.api;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class GoodsApi {

    // Уникальное имя: имена товаров не могут повторяться, а тесты гоняются много раз
    public static String uniqueName(String prefix) {
        return prefix + "-" + UUID.randomUUID();            // например Apple-3f2a9c1e-7b4d-...
    }

    // Создать товар и вернуть его id
    public static int addGood(String name, double price) {
        return given()
                .spec(RestApiBuilder.getAuthBuilder())      // добавлять можно только с логином
                .body(Map.of("name", name, "price", price))  // тело {"name": ..., "price": ...}
                .post(Urls.GOODS_ADD)                        // отправили
                .jsonPath()
                .getInt("data.id");                          // из ответа {"data":{"id":5},...} достали 5
    }

    // Удалить один товар по id
    public static void deleteGood(int id) {
        Response response = given()
                .spec(RestApiBuilder.getAuthBuilder())
                .pathParam("id", id)                         // подставится вместо {id}
                .delete(Urls.GOODS_BY_ID);

        if (response.statusCode() != 200) {                 // не удалился — дальше тесты будут врать
            throw new IllegalStateException("Cleanup failed: DELETE /goods/" + id
                    + " returned " + response.statusCode()
                    + ". Restart the server with a clean database.");
        }
    }

    // Удалить все товары, чтобы тест начинался с пустого склада
    public static void deleteAllGoods() {
        Response response = given()
                .spec(RestApiBuilder.getBuilder())
                .queryParam("size", 1000)                    // список отдаётся по 10 штук — просим сразу 1000
                .get(Urls.GOODS_LIST);

        if (response.statusCode() != 200) {                 // сервер не отдал список — убирать нечего
            throw new IllegalStateException("Cleanup failed: GET /goods/list returned "
                    + response.statusCode()
                    + ". Restart the server with a clean database.");
        }

        List<Integer> ids = response.jsonPath().getList("goods.id", Integer.class);  // id всех товаров
        for (Integer id : ids) {                             // и каждый удаляем
            deleteGood(id);
        }
    }
}