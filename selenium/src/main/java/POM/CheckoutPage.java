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

    @FindBy(id = "checkout_reduction_code")
    public WebElement _couponCode;

    @FindBy(id = "checkout_submit")
    public WebElement _checkoutSubmit;

    @FindBy(xpath = ".//ul[@class='checkout-list']/li//span[@class='cart-line__quantity']")
    public List<WebElement> _quantity;

    @FindBy(xpath = ".//ul[@class='checkout-list']/li//div[@class='cart-line__options']")
    public List<WebElement> _name;

    @FindBy(xpath = ".//ul[@class='checkout-list']/li//div[contains(@class,'cart-line__prices')]/span[contains(@class,'cart-line__price--sale')]")
    public List<WebElement> _price;

    public String getCheckoutPrice() throws InterruptedException {
        return _totalCheckoutPrice.getText();
    }

    public int getTotalItemsCount() throws InterruptedException {
        return _checkedOutItems.size();
    }

    public void enterDetails(String email) throws InterruptedException {
        try{
            Utilities.explicitWait(_couponCode);
            if(_couponCode.isDisplayed()){
                _couponCode.click();
                _couponCode.sendKeys("123");
                _checkoutSubmit.click();
            }
            Utilities.explicitWait(_contactEmailAddress);
            _contactEmailAddress.sendKeys(email);
        }catch (Exception e){

        }
    }

    public boolean verifyItemDetailsOnCheckoutScreen(String quantity, String productName, String price) throws InterruptedException {
        Utilities.explicitWait(_checkedOutItems.get(0));
        for(int i=0;i<_checkedOutItems.size();i++){
           if( _quantity.get(i).getText().contains(quantity) &&
            _name.get(i).getText().contains(productName) &&
            _price.get(i).getText().contains(price)){
               return true;
           }
        }
        return false;
    }
}
