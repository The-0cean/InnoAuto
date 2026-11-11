package org.example.ui;

import org.example.api.GoodsApi;
import org.example.ui.pages.AdminPage;
import org.example.ui.pages.MainPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.example.config.ConfigProvider.CONFIG;

@Tag("ui")
public class ShopScenariosTest extends BaseUiTest {

    @Test
    public void orderOfThreeItemsIsAccepted() {
        GoodsApi.addGood("Ручка", 40);

        MainPage mainPage = new MainPage().open()
                .increaseCount("Ручка")
                .increaseCount("Ручка");
        mainPage.check().countInputHasValue("Ручка", 3);

        mainPage.addToCart("Ручка")
                .check().cartCounterIs(3);

        MainPage.CartPopup cart = mainPage.openCart();
        cart.check()
                .isOpened()
                .totalPriceIs(120);

        cart.makeOrder()
                .check()
                .notificationIsShown("Заказ принят в обработку")
                .titleHasText("Заказ успешно оформлен!")
                .cartCounterIs(0);
    }

    @Test
    public void cartTotalIsSumOfPrices() {
        GoodsApi.addGood("Ручка", 40);
        GoodsApi.addGood("Тетрадь", 70);
        GoodsApi.addGood("Гиря", 120);

        MainPage mainPage = new MainPage().open()
                .addToCart("Ручка")
                .addToCart("Тетрадь")
                .addToCart("Гиря");

        mainPage.openCart()
                .check()
                .itemsCountIs(3)
                .totalPriceIs(230);
    }

    @Test
    public void adminShowsNotificationAfterAdding() {
        AdminPage adminPage = new MainPage().open()
                .clickAdminLink()
                .loginAs(CONFIG.adminLogin(), CONFIG.adminPassword())
                .addGood("Кружка", 99);

        adminPage.check()
                .notificationIsShown("Товар успешно добавлен!")
                .goodIsInTable("Кружка");
    }

    @Test
    public void editedGoodIsUpdatedOnShowcase() {
        int id = GoodsApi.addGood("Кружка", 99);

        AdminPage adminPage = new MainPage().open()
                .clickAdminLink()
                .loginAs(CONFIG.adminLogin(), CONFIG.adminPassword())
                .changeName(id, "Кружка XL")
                .changePrice(id, 111)
                .clickSave(id);
        adminPage.check().notificationIsShown("Товар #" + id + " обновлен");

        adminPage.goToShop()
                .check()
                .productIsShown("Кружка XL")
                .productPriceIs("Кружка XL", 111)
                .productIsNotShown("Кружка");
    }
}
