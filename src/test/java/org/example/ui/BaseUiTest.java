package org.example.ui;

import com.codeborne.selenide.Configuration;
import org.example.api.GoodsApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static org.example.config.ConfigProvider.CONFIG;

public abstract class BaseUiTest {

    @BeforeEach
    public void prepareBrowserAndData() {
        GoodsApi.deleteAllGoods();
        Configuration.baseUrl = CONFIG.baseUrl();
        Configuration.timeout = CONFIG.timeout();
    }

    @AfterEach
    public void closeBrowserAndCleanData() {
        closeWebDriver();
        GoodsApi.deleteAllGoods();
    }
}
