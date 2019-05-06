package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Header extends PageObject {

    public Header(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "login")
    private WebElement loginLink;

    @FindBy(partialLinkText = "Cart")
    private WebElement shoppingCard;

    @FindBy(id = "button_order_cart")
    private WebElement pricesCard;

    public void clickLoginLink() {
        loginLink.click();
    }

    public WebElement moveToShopingCard() {
        return shoppingCard;
    }

    public void openShoppingCard() {
        shoppingCard.click();
    }

    public WebElement checkPriceCard() {
        return pricesCard;
    }
}
