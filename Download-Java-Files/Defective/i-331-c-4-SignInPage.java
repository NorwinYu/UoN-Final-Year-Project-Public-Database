package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage extends PageObject {

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "icon-lock")
    private WebElement signInLink;

    @FindBy(className = "alert")
    private WebElement alertOn;

    public void clickSignInLink() {
        signInLink.click();
    }

    public Boolean isAlertOn() {
        return signInLink.isDisplayed();
    }
}
