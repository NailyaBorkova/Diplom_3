package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage {
    private WebDriver driver;
    private By loginLink = By.xpath("//a[text()='Войти']");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    //Открытие страницы восстановления пароля
    public void open() {
        driver.get("https://stellarburgers.nomoreparties.site/forgot-password");
    }

    //Переход на страницу входа через ссылку 'Войти'
    public void navigateToLogin() {
        driver.findElement(loginLink).click();
    }
}