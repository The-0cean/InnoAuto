package org.example.ui;

import org.example.api.GoodsApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// Задача 1: UI-тесты магазина на «голом» Selenium
@Tag("ui")
public class SeleniumShopTest {

    private static final String BASE_URL = "http://localhost:8080";
    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        GoodsApi.deleteAllGoods();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        GoodsApi.deleteAllGoods();
    }


    private void loginToAdmin(String login, String password) {
        driver.get(BASE_URL + "/");
        driver.findElement(By.cssSelector("a[href='/admin']")).click();
        driver.findElement(By.id("username")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }


    private void addToCart(String name) {
        driver.findElement(By.cssSelector(
                        ".product-card[data-name='" + name + "'] button[data-action='add-to-cart']"))
                .click();
    }


    @Test
    public void goodAddedInAdminIsShownOnShowcase() {
        String name = "Кружка";
        loginToAdmin("admin", "secret123");

        driver.findElement(By.id("n-name")).sendKeys(name);
        driver.findElement(By.id("n-price")).sendKeys("99");
        driver.findElement(By.id("add-btn")).click();
        driver.findElement(By.xpath(
                "//div[@class='toast' and contains(text(), 'успешно добавлен')]"));

        driver.findElement(By.linkText("Вернуться на сайт")).click();
        WebElement title = driver.findElement(By.xpath(
                "//div[@class='product-card']/h4[text()='" + name + "']"));
        assertThat(title.isDisplayed())
                .as("good '%s' must be shown on showcase", name)
                .isTrue();
    }

    // 1.2: добавить товар в корзину и увидеть его там
    @Test
    public void goodAddedToCartIsShownInCart() {
        String name = "Ручка";
        GoodsApi.addGood(name, 40);

        driver.get(BASE_URL + "/");
        addToCart(name);
        assertThat(driver.findElement(By.id("cart-count")).getText())
                .as("cart counter")
                .isEqualTo("1");

        driver.findElement(By.id("open-cart-btn")).click();
        List<WebElement> rows = driver.findElements(By.cssSelector("#cart-items .cart-item"));
        assertThat(rows)
                .as("cart must contain exactly one row")
                .hasSize(1);
        assertThat(rows.get(0).getText())
                .as("cart row")
                .contains(name);
    }

    // 1.3: войти в админку с неверным логином и паролем
    @Test
    public void adminLoginWithWrongCredentialsFails() {
        loginToAdmin("hacker", "qwerty");

        WebElement error = driver.findElement(By.cssSelector(".alert-danger"));
        assertThat(error.isDisplayed())
                .as("error message on login page must be shown")
                .isTrue();
        assertThat(driver.getCurrentUrl())
                .as("must stay on login page")
                .contains("/login?error");
    }

    // 1.4: корзина должна сохраниться после обновления страницы
    @Tag("failing")
    @Test
    public void cartIsKeptAfterPageRefresh() {
        String name = "Тетрадь";
        GoodsApi.addGood(name, 70);

        driver.get(BASE_URL + "/");
        addToCart(name);
        assertThat(driver.findElement(By.id("cart-count")).getText())
                .as("cart counter before refresh")
                .isEqualTo("1");

        driver.navigate().refresh();
        driver.findElement(By.cssSelector(".product-card"));

        assertThat(driver.findElement(By.id("cart-count")).getText())
                .as("cart counter after refresh")
                .isEqualTo("1");
        driver.findElement(By.id("open-cart-btn")).click();
        assertThat(driver.findElements(By.cssSelector("#cart-items .cart-item")))
                .as("cart rows after refresh")
                .hasSize(1);
    }
}