package org.example.ui.pages;

import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;

public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {

    public MainPageAssert(MainPage actual) {
        super(actual, MainPageAssert.class);
    }

    public MainPageAssert titleIsVisible() {
        actual.title.shouldBe(visible);
        return this;
    }

    public MainPageAssert titleHasText(String expected) {
        actual.title.shouldHave(exactText(expected));
        return this;
    }

    public MainPageAssert adminLinkIsVisible() {
        actual.adminLink.shouldBe(visible);
        return this;
    }

    public MainPageAssert cartButtonIsVisible() {
        actual.cartButton.shouldBe(visible);
        return this;
    }

    public MainPageAssert cartCounterIs(int count) {
        actual.cartCounter.shouldHave(exactText(String.valueOf(count)));
        return this;
    }

    public MainPageAssert productsCountIs(int count) {
        actual.productCards.shouldHave(size(count));
        return this;
    }

    public MainPageAssert productIsShown(String name) {
        actual.cardTitle(name).shouldHave(exactText(name));
        return this;
    }

    public MainPageAssert productIsNotShown(String name) {
        actual.card(name).shouldNot(exist);
        return this;
    }

    public MainPageAssert productPriceIs(String name, int price) {
        actual.cardPrice(name).shouldHave(exactText(price + " ₽"));
        return this;
    }

    public MainPageAssert countInputHasValue(String name, int count) {
        actual.countInput(name).shouldHave(value(String.valueOf(count)));
        return this;
    }

    public MainPageAssert plusButtonIsVisible(String name) {
        actual.plusButton(name).shouldBe(visible);
        return this;
    }

    public MainPageAssert minusButtonIsVisible(String name) {
        actual.minusButton(name).shouldBe(visible);
        return this;
    }

    public MainPageAssert addToCartButtonIsVisible(String name) {
        actual.addToCartButton(name).shouldBe(visible);
        return this;
    }

    public MainPageAssert notificationIsShown(String message) {
        actual.notifications.findBy(text(message)).shouldBe(visible);
        return this;
    }

    public MainPage page() {
        return actual;
    }
}
