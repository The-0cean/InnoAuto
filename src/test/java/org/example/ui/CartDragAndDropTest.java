package org.example.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.SelenideElement;
import org.example.api.GoodsApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.example.config.ConfigProvider.CONFIG;

@Tag("ui")
public class CartDragAndDropTest {

    @BeforeEach
    public void setUp() {
        GoodsApi.deleteAllGoods();
        Configuration.baseUrl = CONFIG.baseUrl();
        Configuration.timeout = CONFIG.timeout();
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
        GoodsApi.deleteAllGoods();
    }

    private SelenideElement card(String name) {
        return $(".product-card[data-name='" + name + "']");
    }

    @Test
    public void goodDraggedToCartIsAdded() {
        String name = "Тетрадь";
        GoodsApi.addGood(name, 70);

        open("/");
        card(name).dragAndDrop(DragAndDropOptions.to("#open-cart-btn").usingJS());

        $("#cart-count").shouldHave(exactText("1"));
        $$("#toast-container .toast").findBy(text(name + " (1 шт.) добавлен в корзину")).shouldBe(visible);

        $("#open-cart-btn").click();
        $$("#cart-items .cart-item").shouldHave(size(1));
        $$("#cart-items .cart-item").first().shouldHave(text(name));
        $("#total-price").shouldHave(exactText("70"));
    }

    @Test
    public void goodRemovedFromCartIsNotShown() {
        String name = "Ручка";
        int id = GoodsApi.addGood(name, 40);

        open("/");
        card(name).$("button[data-action='add-to-cart']").click();
        $("#open-cart-btn").click();

        SelenideElement row = $("#cart-item-" + id);
        row.shouldBe(visible);

        row.$("button[data-action='remove']").click();

        row.shouldBe(hidden);
        $$("#cart-items .cart-item").shouldHave(size(0));
        $("#empty-cart").shouldHave(exactText("Пусто"));
        $("#cart-count").shouldHave(exactText("0"));
    }
}