package pageobjects;

import config.Environment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage {
    private WebDriver driver;
    private By loginLink = By.xpath("//a[text()='Войти']");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие страницы восстановления пароля")
    public void open() {
        driver.get(Environment.HOST+Environment.FORGOT_PASSWORD);
    }

    @Step ("Переход на страницу входа через ссылку 'Войти'")
    public void navigateToLogin() {
        driver.findElement(loginLink).click();
    }
}