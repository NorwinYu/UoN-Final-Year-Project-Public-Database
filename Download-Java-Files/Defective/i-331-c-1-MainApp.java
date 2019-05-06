import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page_objects.Header;
import page_objects.SignInPage;

import static org.assertj.core.api.Assertions.assertThat;

public class MainApp {

    private WebDriver driver;
    private WebDriverWait wait;
    private SignInPage signInPage;
    private Header header;

    @BeforeClass
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "lib/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 60);

        signInPage = new SignInPage(driver);
        header = new Header(driver);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void loginErrorTest() {
        driver.get("http://automationpractice.com/index.php");
        header.clickLoginLink();
        signInPage.clickSignInLink();
        assertThat(signInPage.isAlertOn());
    }

    @Test
    public void checkPageTitleTest() {
        driver.get("http://automationpractice.com/index.php");
    }
}

