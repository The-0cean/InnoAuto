package org.example.ui.pages;

import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;

public class AdminLoginPageAssert extends AbstractAssert<AdminLoginPageAssert, AdminLoginPage> {

    public AdminLoginPageAssert(AdminLoginPage actual) {
        super(actual, AdminLoginPageAssert.class);
    }

    public AdminLoginPageAssert loginInputIsVisible() {
        actual.loginInput.shouldBe(visible);
        return this;
    }

    public AdminLoginPageAssert passwordInputIsVisible() {
        actual.passwordInput.shouldBe(visible);
        return this;
    }

    public AdminLoginPageAssert signInButtonIsVisible() {
        actual.signInButton.shouldBe(visible);
        return this;
    }

    public AdminLoginPageAssert loginInputHasValue(String expected) {
        actual.loginInput.shouldHave(value(expected));
        return this;
    }

    public AdminLoginPageAssert passwordInputHasValue(String expected) {
        actual.passwordInput.shouldHave(value(expected));
        return this;
    }

    public AdminLoginPage page() {
        return actual;
    }
}
