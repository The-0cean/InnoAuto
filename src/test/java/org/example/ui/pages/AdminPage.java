package org.example.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AdminPage {

    final SelenideElement nameInput = $("#n-name");
    final SelenideElement priceInput = $("#n-price");
    final SelenideElement createButton = $("#add-btn");
    final SelenideElement backToShopLink = $("a[href='/']");
    final ElementsCollection tableNameInputs = $$("#tbody input[id^='nm-']");
    final ElementsCollection notifications = $$("#toast-container .toast");

    SelenideElement rowNameInput(int id) {
        return $("#nm-" + id);
    }

    SelenideElement rowPriceInput(int id) {
        return $("#pr-" + id);
    }

    SelenideElement rowSaveButton(int id) {
        return $("button[data-action='update'][data-id='" + id + "']");
    }

    public AdminPage enterName(String name) {
        nameInput.setValue(name);
        return this;
    }

    public AdminPage enterPrice(int price) {
        priceInput.setValue(String.valueOf(price));
        return this;
    }

    public AdminPage clickCreate() {
        createButton.click();
        return this;
    }

    public AdminPage addGood(String name, int price) {
        return enterName(name)
                .enterPrice(price)
                .clickCreate();
    }

    public AdminPage changeName(int id, String name) {
        rowNameInput(id).setValue(name);
        return this;
    }

    public AdminPage changePrice(int id, int price) {
        rowPriceInput(id).setValue(String.valueOf(price));
        return this;
    }

    public AdminPage clickSave(int id) {
        rowSaveButton(id).click();
        return this;
    }

    public MainPage goToShop() {
        backToShopLink.click();
        return new MainPage();
    }

    public List<String> getNotificationTexts() {
        return notifications.texts();
    }

    public AdminPageAssert check() {
        return new AdminPageAssert(this);
    }
}
