package org.example.ui.pages;

import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;

public class AdminPageAssert extends AbstractAssert<AdminPageAssert, AdminPage> {

    public AdminPageAssert(AdminPage actual) {
        super(actual, AdminPageAssert.class);
    }

    public AdminPageAssert nameInputIsVisible() {
        actual.nameInput.shouldBe(visible);
        return this;
    }

    public AdminPageAssert priceInputIsVisible() {
        actual.priceInput.shouldBe(visible);
        return this;
    }

    public AdminPageAssert createButtonIsVisible() {
        actual.createButton.shouldBe(visible);
        return this;
    }

    public AdminPageAssert backToShopLinkIsVisible() {
        actual.backToShopLink.shouldBe(visible);
        return this;
    }

    public AdminPageAssert goodIsInTable(String name) {
        actual.tableNameInputs.findBy(value(name)).shouldBe(visible);
        return this;
    }

    public AdminPageAssert notificationIsShown(String message) {
        actual.notifications.findBy(text(message)).shouldBe(visible);
        return this;
    }

    public AdminPage page() {
        return actual;
    }
}
