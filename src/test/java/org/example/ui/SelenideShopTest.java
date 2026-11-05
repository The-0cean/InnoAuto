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
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.confirm;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;

// Задача 2: те же кейсы на Selenide + проверка JS Alert
@Tag("ui")
public class SelenideShopTest {

    @BeforeEach
    public void setUp() {
        GoodsApi.deleteAllGoods();
        Configuration.baseUrl = "http://localhost:8080";
        Configuration.timeout = 5000;
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
        GoodsApi.deleteAllGoods();
    }

    // Вход в админку с главной страницы
    private void loginToAdmin(String login, String password) {
        open("/");
        $("a[href='/admin']").click();
        $("#username").setValue(login);
        $("#password").setValue(password);
        $("button[type='submit']").click();
    }

    // Карточка товара по названию
    private SelenideElement card(String name) {
        return $(".product-card[data-name='" + name + "']");
    }

    // 2.1: добавить товар через админку и увидеть его на витрине
    @Test
    public void goodAddedInAdminIsShownOnShowcase() {
        String name = "Кружка";
        loginToAdmin("admin", "secret123");

        $("#n-name").setValue(name);
        $("#n-price").setValue("99");
        $("#add-btn").click();
        $(".toast").shouldHave(text("успешно добавлен"));

        $("a[href='/']").click();
        $$(".product-card h4").findBy(exactText(name)).shouldBe(visible);
    }

    // 2.2: добавить товар в корзину и увидеть его там
    @Test
    public void goodAddedToCartIsShownInCart() {
        String name = "Ручка";
        GoodsApi.addGood(name, 40);

        open("/");
        card(name).$("button[data-action='add-to-cart']").click();
        $("#cart-count").shouldHave(exactText("1"));

        $("#open-cart-btn").click();
        $$("#cart-items .cart-item").shouldHave(size(1));
        $$("#cart-items .cart-item").first().shouldHave(text(name));
    }

    // 2.3: войти в админку с неверным логином и паролем
    @Test
    public void adminLoginWithWrongCredentialsFails() {
        loginToAdmin("hacker", "qwerty");

        $(".alert-danger").shouldBe(visible);
        $("#username").shouldBe(visible);
        $("#n-name").shouldNot(exist);
    }

    // 2.4: корзина должна сохраниться после обновления страницы
    @Tag("failing")
    @Test
    public void cartIsKeptAfterPageRefresh() {
        String name = "Тетрадь";
        GoodsApi.addGood(name, 70);

        open("/");
        card(name).$("button[data-action='add-to-cart']").click();
        $("#cart-count").shouldHave(exactText("1"));

        refresh();
        card(name).shouldBe(visible);
        $("#cart-count").shouldHave(exactText("1"));
    }

    // 2.5: корзина дороже 300 ₽ → «Оформить заказ» → JS Alert
    @Test
    public void orderOver300ShowsAlert() {
        String name = "Гиря";
        GoodsApi.addGood(name, 120);

        open("/");
        card(name).$("button[data-step='1']").click();
        card(name).$("button[data-step='1']").click();
        card(name).$(".qty-input").shouldHave(value("3"));
        card(name).$("button[data-action='add-to-cart']").click();

        $("#open-cart-btn").click();
        $("#total-price").shouldHave(exactText("360"));
        $("#makeOrder").click();

        confirm("[SmartShop]: Денег не хватает! Сумма 360 ₽ превышает лимит 300 ₽.");
        $("#main-title").shouldNotHave(text("Заказ успешно оформлен"));
    }
}