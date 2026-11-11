package org.example.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AdminLoginPage {

    final SelenideElement loginInput = $("#username");
    final SelenideElement passwordInput = $("#password");
    final SelenideElement signInButton = $("button[type='submit']");

    public AdminLoginPage enterLogin(String login) {
        loginInput.setValue(login);
        return this;
    }

    public AdminLoginPage enterPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public AdminPage clickSignIn() {
        signInButton.click();
        return new AdminPage();
    }

    public AdminPage loginAs(String login, String password) {
        return enterLogin(login)
                .enterPassword(password)
                .clickSignIn();
    }

    public AdminLoginPageAssert check() {
        return new AdminLoginPageAssert(this);
    }
}
