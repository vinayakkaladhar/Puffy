package POM;

import Utilities.Utilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage extends Utilities {

    public CheckoutPage() {
        driver = getDriver();
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = ".//span[contains(@class,'payment-due__price')]")
    public WebElement _totalCheckoutPrice;

    @FindBy(xpath = ".//ul[@class='checkout-list']/li")
    public List<WebElement> _checkedOutItems;

    @FindBy(id = "checkout_email")
    public WebElement _contactEmailAddress;

    public String getCheckoutPrice() throws InterruptedException {
        return _totalCheckoutPrice.getText();
    }

    public int getTotalItemsCount() throws InterruptedException {
        return _checkedOutItems.size();
    }

    public void enterDetails(String email) throws InterruptedException {
        Utilities.explicitWait(_contactEmailAddress);
        driver.navigate().refresh();
        _contactEmailAddress.sendKeys(email);

    }
}
