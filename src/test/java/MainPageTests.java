import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.MainPage;
import pageObjects.WebDriverFactory;

import static org.junit.Assert.*;

public class MainPageTests {

    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {

        String browserName = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.createDriver(browserName);
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        mainPage.open();

    }

    @Test
    public void testBunTabOpens() {

        //mainPage.clickSauceTab();
        //mainPage.clickBunTab();
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        assertTrue("Bun tab is not active", mainPage.isBunTabActive());
    }

    @Test
    public void testSauceTabOpens() {

        mainPage.clickSauceTab();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertTrue("Sauce tab is not active", mainPage.isSauceTabActive());

    }

    @Test
    public void testFillingTabOpens() {

        mainPage.clickFillingTab();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        assertTrue("Filling tab is not active", mainPage.isFillingTabActive());

    }

    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

}