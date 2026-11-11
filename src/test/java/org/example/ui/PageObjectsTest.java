package org.example.ui;

import org.example.api.GoodsApi;
import org.example.ui.pages.AdminLoginPage;
import org.example.ui.pages.MainPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.example.config.ConfigProvider.CONFIG;

@Tag("ui")
public class PageObjectsTest extends BaseUiTest {

    @Test
    public void mainPageElementsWork() {
        GoodsApi.addGood("Ручка", 40);

        MainPage mainPage = new MainPage().open();
        mainPage.check()
                .titleIsVisible()
                .adminLinkIsVisible()
                .cartButtonIsVisible()
                .cartCounterIs(0)
                .productsCountIs(1)
                .productIsShown("Ручка")
                .productPriceIs("Ручка", 40)
                .plusButtonIsVisible("Ручка")
                .minusButtonIsVisible("Ручка")
                .addToCartButtonIsVisible("Ручка")
                .countInputHasValue("Ручка", 1);

        mainPage.setCount("Ручка", 5)
                .decreaseCount("Ручка")
                .check()
                .countInputHasValue("Ручка", 4);

        mainPage.dragToCart("Ручка")
                .check()
                .cartCounterIs(4)
                .notificationIsShown("Ручка (4 шт.) добавлен в корзину");

        MainPage.CartPopup cart = mainPage.openCart();
        cart.check()
                .isOpened()
                .closeButtonIsVisible()
                .orderButtonIsVisible()
                .containsItem("Ручка")
                .totalPriceIs(160);

        cart.removeItem("Ручка")
                .check()
                .itemsCountIs(0)
                .totalPriceIs(0);

        cart.close()
                .check()
                .cartCounterIs(0);
        cart.check().isClosed();
    }

    @Test
    public void adminLoginPageElementsWork() {
        AdminLoginPage loginPage = new MainPage().open().clickAdminLink();
        loginPage.check()
                .loginInputIsVisible()
                .passwordInputIsVisible()
                .signInButtonIsVisible();

        loginPage.enterLogin(CONFIG.adminLogin())
                .enterPassword(CONFIG.adminPassword())
                .check()
                .loginInputHasValue(CONFIG.adminLogin())
                .passwordInputHasValue(CONFIG.adminPassword());

        loginPage.clickSignIn()
                .check()
                .nameInputIsVisible()
                .priceInputIsVisible()
                .createButtonIsVisible()
                .backToShopLinkIsVisible();
    }
}
