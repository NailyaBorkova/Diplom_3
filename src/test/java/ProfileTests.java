import api.AuthApi;
import api.User;
import api.UserLogin;
import generators.UserGenerators;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.*;
import pageObjects.WebDriverFactory;

public class ProfileTests {
    private AuthApi authApi;
    private User user;
    private UserLogin login;
    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ProfilePage profilePage;
    private String bearerToken;
    private String token;

    @Before
    public void setUp() {

        String browserName = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.createDriver(browserName);

        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
        profilePage = new ProfilePage(driver);

        authApi = new AuthApi();
        user = UserGenerators.getSuccessCreateUser();
        login = new UserLogin();

        ValidatableResponse responseCreate = authApi.createUserRequest(user);
        bearerToken = responseCreate.extract().path("accessToken");
        token = bearerToken.substring(7);

        authApi.loginUserRequest(login.from(user));

    }

    @Test
    public void goToProfileFromMainPage() {

        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getProfileLink()));
        mainPage.goToProfile();
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/account/profile"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL не соответствует ожидаемому", "https://stellarburgers.nomoreparties.site/account/profile", currentUrl);

    }

    @Test
    public void goToMainPageByLogoFromProfile() {

        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.goToProfile();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(profilePage.getLogo()));
        profilePage.clickLogo();
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL не соответствует ожидаемому", "https://stellarburgers.nomoreparties.site/", currentUrl);

    }

    @Test
    public void goToMainPageByButtonFromProfile() {

        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.goToProfile();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(profilePage.getConstructorButton()));
        profilePage.goToMainByConstructorButton();
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL не соответствует ожидаемому", "https://stellarburgers.nomoreparties.site/", currentUrl);

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if(token != null){
            authApi.deleteUserRequest(token);
        }
    }
}