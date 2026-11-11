package org.example.ui.pages;

import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    final SelenideElement title = $("#main-title");
    final SelenideElement adminLink = $("a[href='/admin']");
    final SelenideElement cartButton = $("#open-cart-btn");
    final SelenideElement cartCounter = $("#cart-count");
    final ElementsCollection productCards = $$(".product-card");
    final ElementsCollection notifications = $$("#toast-container .toast");

    private final CartPopup cart = new CartPopup();

    SelenideElement card(String name) {
        return $(".product-card[data-name='" + name + "']");
    }

    SelenideElement cardTitle(String name) {
        return card(name).$("h4");
    }

    SelenideElement cardPrice(String name) {
        return card(name).$("h4 + div");
    }

    SelenideElement countInput(String name) {
        return card(name).$(".qty-input");
    }

    SelenideElement plusButton(String name) {
        return card(name).$("button[data-step='1']");
    }

    SelenideElement minusButton(String name) {
        return card(name).$("button[data-step='-1']");
    }

    SelenideElement addToCartButton(String name) {
        return card(name).$("button[data-action='add-to-cart']");
    }

    public MainPage open() {
        Selenide.open("/");
        return this;
    }

    public AdminLoginPage clickAdminLink() {
        adminLink.click();
        return new AdminLoginPage();
    }

    public CartPopup openCart() {
        cartButton.click();
        return cart;
    }

    public MainPage increaseCount(String name) {
        plusButton(name).click();
        return this;
    }

    public MainPage decreaseCount(String name) {
        minusButton(name).click();
        return this;
    }

    public MainPage setCount(String name, int count) {
        countInput(name).setValue(String.valueOf(count));
        return this;
    }

    public MainPage addToCart(String name) {
        addToCartButton(name).click();
        return this;
    }

    public MainPage dragToCart(String name) {
        card(name).dragAndDrop(DragAndDropOptions.to(cartButton).usingJS());
        return this;
    }

    public String getTitleText() {
        return title.getText();
    }

    public String getCartCount() {
        return cartCounter.getText();
    }

    public List<String> getProductNames() {
        return productCards.texts();
    }

    public List<String> getNotificationTexts() {
        return notifications.texts();
    }

    public MainPageAssert check() {
        return new MainPageAssert(this);
    }

    public class CartPopup {

        final SelenideElement window = $("#cartModal");
        final ElementsCollection items = $$("#cart-items .cart-item");
        final SelenideElement totalPrice = $("#total-price");
        final SelenideElement orderButton = $("#makeOrder");
        final SelenideElement closeButton = $("#close-modal");

        SelenideElement item(String name) {
            return items.findBy(text(name));
        }

        public CartPopup increaseItemCount(String name) {
            item(name).$("button[data-step='1']").click();
            return this;
        }

        public CartPopup removeItem(String name) {
            item(name).$("button[data-action='remove']").click();
            return this;
        }

        public MainPage makeOrder() {
            orderButton.click();
            return MainPage.this;
        }

        public MainPage close() {
            closeButton.click();
            return MainPage.this;
        }

        public String getTotalPrice() {
            return totalPrice.getText();
        }

        public CartPopupAssert check() {
            return new CartPopupAssert(this);
        }
    }
}
