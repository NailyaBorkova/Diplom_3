import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObjects.RegistrationPage;
import pageObjects.WebDriverFactory;

import java.util.UUID;
import static org.junit.Assert.assertTrue;

public class RegistrationTests {

    private WebDriver driver;
    private RegistrationPage registrationPage;

    @Before
    public void setUp() {

        String browserName = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.createDriver(browserName);

        driver.manage().window().maximize();
        registrationPage = new RegistrationPage(driver);
        registrationPage.open();

    }

    @Test
    public void testSuccessfulRegistration() {

        String username = "TestUser" + UUID.randomUUID().toString().substring(0, 8);
        String emailPrefix = "testuser";
        String emailDomain = "example.com";
        String email = emailPrefix + UUID.randomUUID().toString().substring(0, 8) + "@" + emailDomain;
        registrationPage.register(username, email, "password123");
        assertTrue("The user should be registered successfully", registrationPage.isSuccessfulRegistration());

    }

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

    }
}