package org.example.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.example.api.GoodsApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

// Задача 3 (вебинар): сценарии магазина на Selenide
@Tag("ui")
public class ShopScenariosTest {

    @BeforeEach
    public void setUp() {
        GoodsApi.deleteAllGoods();
        Configuration.baseUrl = "http://localhost:8080";
        Configuration.timeout = 5000;
    }

    // 3.6: тестовые данные удаляются в @AfterEach
    @AfterEach
    public void tearDown() {
        closeWebDriver();
        GoodsApi.deleteAllGoods();
    }

    private void loginToAdmin() {
        open("/");
        $("a[href='/admin']").click();
        $("#username").setValue("admin");
        $("#password").setValue("secret123");
        $("button[type='submit']").click();
    }

    private SelenideElement card(String name) {
        return $(".product-card[data-name='" + name + "']");
    }

    // 3.1: три штуки товара в корзину, оплатить (≤ 300 ₽), проверить уведомление
    @Test
    public void orderOfThreeItemsIsAccepted() {
        String name = "Ручка";
        GoodsApi.addGood(name, 40);

        open("/");
        card(name).$("button[data-step='1']").click();
        card(name).$("button[data-step='1']").click();
        card(name).$(".qty-input").shouldHave(value("3"));
        card(name).$("button[data-action='add-to-cart']").click();
        $("#cart-count").shouldHave(exactText("3"));

        $("#open-cart-btn").click();
        $("#total-price").shouldHave(exactText("120"));
        $("#makeOrder").click();

        $$("#toast-container .toast")
                .findBy(text("Заказ принят в обработку"))
                .shouldBe(visible);
        $("#main-title").shouldHave(exactText("Заказ успешно оформлен!"));
        $("#cart-count").shouldHave(exactText("0"));
    }

    // 3.2: несколько разных товаров — общая цена считается правильно
    @Test
    public void cartTotalIsSumOfPrices() {
        GoodsApi.addGood("Ручка", 40);
        GoodsApi.addGood("Тетрадь", 70);
        GoodsApi.addGood("Гиря", 120);

        open("/");
        card("Ручка").$("button[data-action='add-to-cart']").click();
        card("Тетрадь").$("button[data-action='add-to-cart']").click();
        card("Гиря").$("button[data-action='add-to-cart']").click();

        $("#open-cart-btn").click();
        $$("#cart-items .cart-item").shouldHave(size(3));
        $("#total-price").shouldHave(exactText("230"));
    }

    // 3.3: войти в админку, добавить товар, проверить уведомление
    @Test
    public void adminShowsNotificationAfterAdding() {
        loginToAdmin();

        $("#n-name").setValue("Кружка");
        $("#n-price").setValue("99");
        $("#add-btn").click();

        $(".toast").shouldHave(exactText("Товар успешно добавлен!"));
        $("#tbody input[value='Кружка']").shouldBe(visible);
    }

    // 3.4: войти в админку, отредактировать товар, проверить изменения на витрине
    @Test
    public void editedGoodIsUpdatedOnShowcase() {
        int id = GoodsApi.addGood("Кружка", 99);
        loginToAdmin();

        $("#nm-" + id).setValue("Кружка XL");
        $("#pr-" + id).setValue("111");
        $("button[data-action='update'][data-id='" + id + "']").click();
        $$(".toast").findBy(text("Товар #" + id + " обновлен")).shouldBe(visible);

        $("a[href='/']").click();
        SelenideElement editedCard = $(".product-card[data-id='" + id + "']");
        editedCard.$("h4").shouldHave(exactText("Кружка XL"));
        editedCard.$("h4 + div").shouldHave(exactText("111 ₽"));
    }
}