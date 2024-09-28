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
import pageObjects.LoginPage;
import pageObjects.MainPage;
import pageObjects.PasswordRecoveryPage;
import pageObjects.ProfilePage;
import pageObjects.RegistrationPage;
import pageObjects.WebDriverFactory;
public class LoginTests {

    private AuthApi authApi;
    private User user;
    private UserLogin login;
    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private PasswordRecoveryPage passwordRecoveryPage;
    private RegistrationPage registrationPage;
    private ProfilePage profilePage;
    private String bearerToken;
    private String token;

    @Before
    public void setUp() {

        String browserName = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.createDriver(browserName);

        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);
        registrationPage = new RegistrationPage(driver);
        profilePage = new ProfilePage(driver);
        driver.manage()
                .window()
                .maximize();

        authApi = new AuthApi();
        user = UserGenerators.getSuccessCreateUser();
        login = new UserLogin();

        ValidatableResponse responseCreate = authApi.createUserRequest(user);
        bearerToken = responseCreate.extract().path("accessToken");
        token = bearerToken.substring(7);

        authApi.loginUserRequest(login.from(user));

    }

    @Test
    public void testLoginFromMainPage() {

        mainPage.open();
        mainPage.navigateToLogin();
        loginPage.login(user.getEmail(), user.getPassword());
        Assert.assertTrue("Login failed", loginPage.isLoginSuccessful());

    }

    @Test
    public void testLoginFromRegistrationPage() {
        registrationPage.open();
        registrationPage.navigateToLogin();
        loginPage.login(user.getEmail(), user.getPassword());
        Assert.assertTrue("Login failed", loginPage.isLoginSuccessful());
    }

    @Test
    public void testLoginAfterPasswordRecovery() {

        passwordRecoveryPage.open();
        passwordRecoveryPage.navigateToLogin();
        loginPage.login(user.getEmail(), user.getPassword());
        Assert.assertTrue("Login failed", loginPage.isLoginSuccessful());

    }

    @Test
    public void testLogout() {

        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.goToProfile();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(profilePage.getLogoutButttonElement()));
        wait.until(ExpectedConditions.elementToBeClickable(profilePage.getLogoutButton())).click();
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/login"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("https://stellarburgers.nomoreparties.site/login", currentUrl);

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