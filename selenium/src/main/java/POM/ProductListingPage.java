package POM;

import Utilities.Utilities;
import io.cucumber.java.bs.A;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductListingPage extends Utilities {

    public ProductListingPage() {
        driver = getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = ".//h1[contains(text(),'Puffy Lux Mattress')]")
    public WebElement _inventory;

    @FindBy(xpath = ".//div[@id='panel-mattress-features']/div/p")
    public WebElement _features;

    @FindBy(xpath = ".//h2[contains(text(),'Reasons To Choose Puffy')]")
    public WebElement _reasonsTab;

    @FindBy(xpath = ".//div[@id='panel-mattress-reasons']/div/ol/li")
    public List<WebElement> _reasons;

    @FindBy(xpath = ".//div[@class='product-head__reviews']//span[@class='jdgm-prev-badge__stars']")
    public WebElement _productStars;

    @FindBy(xpath = ".//div[@class='product-head__reviews']//span[contains(text(),'Reviews')]")
    public WebElement _productReviews;

    @FindBy(xpath = ".//div[@class='jdgm-rev-widg__reviews']")
    public WebElement _productReviewsSection;

    @FindBy(xpath = ".//div[@class='jdgm-rev-widg__reviews']/div")
    public List<WebElement> _productReviewsSectionList;

    @FindBy(xpath = ".//div[@class='product-head__reviews']//div[@class='product-head__faq__text']")
    public WebElement _fAQS;

    @FindBy(xpath = ".//div[@class='faq-content__boxes']")
    public WebElement _fAQSSection;

    @FindBy(xpath = ".//ul[@class='questions-list']/li")
    public List<WebElement> _fAQSQuestionsList;

    @FindBy(xpath = ".//div[@class='slick-track']/div[contains(@class,'product-thumbs__item')]")
    public List<WebElement> _productImages;

    @FindBy(xpath = ".//div[contains(@class,'slick-active')]//picture/img")
    public WebElement _currentProductImage;

    @FindBy(xpath = ".//div[contains(@class,'cart_count')]/span")
    public WebElement _cartCount;

    @FindBy(xpath = ".//div[@id='subscribe-popup']/div/div")
    public WebElement _closeButton;

    @FindBy(xpath = ".//a[contains(@class,'mini_cart')]")
    public WebElement _cart;

    @FindBy(xpath = ".//div[contains(@class,'pd-variants__size')]")
    public WebElement _selectedProductSize;

    @FindBy(xpath = ".//span[contains(@class,'js-variantOldPrice')]")
    public WebElement _selectedProductPrize;

    @FindBy(xpath = ".//span[contains(@class,'js-variantNewPrice')]")
    public WebElement _selectedProductDiscountedPrize;

    @FindBy(xpath = ".//span[@id='jsShippingEstimate']")
    public WebElement _selectedProductShippingEstimate;

    @FindBy(xpath = ".//*[@class='delivery-date__text']/Strong")
    public WebElement _selectedProductShippingStatus;

    @FindBy(xpath = ".//div[@class='cart-checkout__action']//span")
    public WebElement _checkoutButton;

    @FindBy(xpath = ".//div[contains(@class,'upsell-section__footer')]//div[contains(@class,'free-acc-box__title')]")
    public List<WebElement> _freeBundle;

    @FindBy(xpath = ".//div[contains(@class,'popup__close')]")
    public WebElement _popUp;

    public void navigateTo(String url) throws InterruptedException {
        driver.get(url);
        Utilities.explicitWait(_inventory);
    }

    public boolean verifyProductIsListed(String productName) throws InterruptedException {
        Utilities.explicitWait(_inventory);
        return _inventory.isDisplayed();
    }

    public boolean verifyAdditionalDetailsDisplayed(String about,String reason, int reasonsToBuy) throws InterruptedException {
        boolean result;
        result = _features.getText().contains(about);
        _reasonsTab.click();
        for(WebElement e: _reasons){
            if(e.getText().contains(reason)){
                result=true;
            }
        }
        result = result && _reasons.size()==reasonsToBuy;
        return result;
    }

    public boolean verifyProductRatingDisplayed(String rating) throws InterruptedException {
        boolean result;
        result = _productStars.getAttribute("data-score").contains(rating);
        return result;
    }

    public boolean verifyNavigationToSection(String reviewSection, String productFAQSection) throws InterruptedException {
        boolean result;
        _productReviews.click();
        result = _productReviewsSection.isDisplayed();
        result = result && _productReviewsSectionList.size()>1;
        Actions action =  new Actions(driver);
        try{
            if(_popUp.isDisplayed()){
                _popUp.click();
            }
        }catch(Exception e){

        }
        driver.navigate().refresh();
        action.moveToElement(_fAQS).click().perform();
        result = result && _fAQSSection.isDisplayed();
        result = result && _fAQSQuestionsList.size()>1;
        return result;
    }

    public boolean verifyProductImagesDisplayed(int numberOfImages) throws InterruptedException {
        boolean result;
        _productImages.get(3).click();
        Thread.sleep(1000);
        result = _productImages.size()>=numberOfImages;
        return result;
    }

    public String getCurrentlyDisplayedImageSource() throws InterruptedException {
        Utilities.explicitWait(_currentProductImage);
        return _currentProductImage.getAttribute("src");
    }

    public void clickOnButton(String buttonName) throws InterruptedException {
        Utilities.explicitWait(driver.findElement(By.xpath(String.format(".//span[text()='%s']",buttonName))));
        driver.findElement(By.xpath(String.format(".//span[text()='%s']",buttonName))).click();
        Thread.sleep(3000);
    }

    public String getCartCount() throws InterruptedException {
        Utilities.explicitWait(_cartCount);
       return _cartCount.getText();
    }

    public void closeSubscriptionPopUp() throws InterruptedException {
        try{
            Utilities.explicitWait(_closeButton);
            _closeButton.click();
        }catch (Exception e){

        }
    }

    public boolean verifyModelSelectedIs(String modelName) throws InterruptedException {
        return driver.findElement(By.xpath(String.format(".//input[@data-handle='%s']",modelName))).isSelected();
    }

    public void clickOnModel(String modelName) throws InterruptedException {
        Utilities.explicitWait(driver.findElement(By.xpath(String.format(".//div[contains(@class,'pd-buttons__item')]/label[text()='%s']",modelName))));
         driver.findElement(By.xpath(String.format(".//div[contains(@class,'pd-buttons__item')]/label[text()='%s']",modelName))).click();
    }

    public boolean verifySelectedModelSize(String size) throws InterruptedException {
        return _selectedProductSize.getText().contains(size);
    }

    public String getSelectedModelSize() throws InterruptedException {
        return _selectedProductSize.getText();
    }

    public String getSelectedModelPrice() throws InterruptedException {
        return _selectedProductPrize.getText();
    }

    public String getSelectedModelDiscountedPrice() throws InterruptedException {
        return _selectedProductDiscountedPrize.getText();
    }

    public String getSelectedModelShippingEstimate() throws InterruptedException {
        return _selectedProductShippingEstimate.getText();
    }

    public void clickOnCartAndCheckout() throws InterruptedException {
        Utilities.explicitWait(_cart);
        _cart.click();
        Utilities.explicitWait(_checkoutButton);
        _checkoutButton.click();
    }

    public void clickOnCheckout() throws InterruptedException {
        Utilities.explicitWait(_checkoutButton);
        _checkoutButton.click();
    }

    public int getFreeBundleForSelectedProduct() throws InterruptedException {
        return _freeBundle.size();
    }

    public boolean verifyShipmentStatus(String status) throws InterruptedException {
        return _selectedProductShippingStatus.getText().contains(status);
    }
}
