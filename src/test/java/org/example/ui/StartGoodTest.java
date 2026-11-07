package org.example.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.example.api.GoodsApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.example.config.ConfigProvider.CONFIG;

@Tag("ui")
public class StartGoodTest {

    private int startGoodId;

    @BeforeEach
    public void setUp() {
        GoodsApi.deleteAllGoods();
        Configuration.baseUrl = CONFIG.baseUrl();
        Configuration.timeout = CONFIG.timeout();
        startGoodId = GoodsApi.addGood(CONFIG.startGoodName(), CONFIG.startGoodPrice());
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
        GoodsApi.deleteAllGoods();
    }

    @Test
    public void startGoodIsShownOnShowcase() {
        open("/");

        SelenideElement card = $(".product-card[data-id='" + startGoodId + "']");
        card.$("h4").shouldHave(exactText(CONFIG.startGoodName()));
        card.$("h4 + div").shouldHave(exactText(CONFIG.startGoodPrice() + " ₽"));
    }

    @Test
    public void startGoodIsListedInAdmin() {
        open("/");
        $("a[href='/admin']").click();
        $("#username").setValue(CONFIG.adminLogin());
        $("#password").setValue(CONFIG.adminPassword());
        $("button[type='submit']").click();

        $("#nm-" + startGoodId).shouldHave(value(CONFIG.startGoodName()));
        $("#pr-" + startGoodId).shouldHave(value(String.valueOf(CONFIG.startGoodPrice())));
    }
}
