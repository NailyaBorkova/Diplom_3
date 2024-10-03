import api.AuthApi;
import api.User;
import api.UserLogin;
import generators.UserGenerators;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.RegistrationPage;
import pageobjects.WebDriverFactory;

import java.util.UUID;
import static org.junit.Assert.assertTrue;

public class RegistrationTests {

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private String bearerToken;
    private String token;
    private User user;
    private UserLogin login;
    private AuthApi authApi;

    @Before
    public void setUp() {

        String browserName = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.createDriver(browserName);

        driver.manage().window().maximize();
        registrationPage = new RegistrationPage(driver);
        registrationPage.open();

        authApi = new AuthApi();
        user = UserGenerators.getSuccessCreateUser();
        login = new UserLogin(user.getEmail(), user.getPassword());

    }

    @Description("Тест успешной регистрации")
    @Test
    public void testSuccessfulRegistration() {

        registrationPage.register(user.getName(), user.getEmail(), user.getPassword());
        assertTrue("The user should be registered successfully", registrationPage.isSuccessfulRegistration());
        ValidatableResponse responseCreate = authApi.loginUserRequest(login);
        bearerToken = responseCreate.extract().path("accessToken");
        token = bearerToken.substring(7);

    }

    @Description("Тест. Ошибка для некорректного пароля. Минимальный пароль — шесть символов")
    @Test
    public void testRegistrationWithShortPassword() {

        registrationPage.register("TestUser", "testuser@example.com", "pass");
        assertTrue("An error message for short password should be displayed", registrationPage.isPasswordErrorDisplayed());

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