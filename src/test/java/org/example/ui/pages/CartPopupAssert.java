package org.example.ui.pages;

import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;

public class CartPopupAssert extends AbstractAssert<CartPopupAssert, MainPage.CartPopup> {

    public CartPopupAssert(MainPage.CartPopup actual) {
        super(actual, CartPopupAssert.class);
    }

    public CartPopupAssert isOpened() {
        actual.window.shouldBe(visible);
        return this;
    }

    public CartPopupAssert isClosed() {
        actual.window.shouldBe(hidden);
        return this;
    }

    public CartPopupAssert itemsCountIs(int count) {
        actual.items.shouldHave(size(count));
        return this;
    }

    public CartPopupAssert containsItem(String name) {
        actual.item(name).shouldBe(visible);
        return this;
    }

    public CartPopupAssert totalPriceIs(int price) {
        actual.totalPrice.shouldHave(exactText(String.valueOf(price)));
        return this;
    }

    public CartPopupAssert orderButtonIsVisible() {
        actual.orderButton.shouldBe(visible);
        return this;
    }

    public CartPopupAssert closeButtonIsVisible() {
        actual.closeButton.shouldBe(visible);
        return this;
    }

    public MainPage.CartPopup page() {
        return actual;
    }
}
