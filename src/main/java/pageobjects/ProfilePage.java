package pageobjects;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProfilePage {

    private WebDriver driver;

    private By logoutButton = By.xpath("//button[contains(text(), 'Выход')]");
    private By constructorButton = By.xpath(".//a[@href='/']/p[text()='Конструктор']");
    private By logo = By.xpath(".//a[@class='AppHeader_header__link__3D_hX']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Получение кнопки выхода")
    public WebElement getLogoutButton() {
        return driver.findElement(logoutButton);
    }

    @Step ("Переход на главную страницу через кнопку 'Конструктор'")
    public void goToMainByConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step ("Клик по логотипу для перехода на главную страницу")
    public void clickLogo() {
        driver.findElement(logo).click();
    }

    public By getLogo() {
        return logo;
    }

    public By getLogoutButttonElement() {
        return logoutButton;
    }

    public By getConstructorButton() {
        return constructorButton;
    }
}