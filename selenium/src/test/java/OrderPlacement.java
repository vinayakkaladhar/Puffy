import POM.CheckoutPage;
import POM.ProductListingPage;
import Utilities.ListenerTest;
import Utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

@Listeners(ListenerTest.class)
public class OrderPlacement extends Utilities{
    public CheckoutPage checkoutPage;
    public ProductListingPage productListingPage;
    boolean result;
    public Properties properties;
    public WebDriver driver;


    @BeforeClass
    public void getCredentials() throws IOException {
        properties  = Utilities.readPropertiesFile(System.getProperty("user.dir")+ "/src/main/resources/" + "config.properties");
    }

    @BeforeMethod
    public void setUpOpen() {
        driver = Utilities.getDriver();
        driver.manage().deleteAllCookies();
        checkoutPage = new CheckoutPage();
        productListingPage = new ProductListingPage();
    }

    @Test(description = "Verify successful redirection to puffy lux mattress on using deep link")
    public void verifyProductIsListedOnUsingDeepLink() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        Assert.assertTrue(productListingPage.verifyProductIsListed(properties.getProperty("product")), "Product is listed as expected on redirection.");
    }

    @Test(description = "Verify product features and reasons to buy are displayed as expected")
    public void verifyProductFeaturesAndReasonsToBuy() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        result = productListingPage.verifyAdditionalDetailsDisplayed(properties.getProperty("about"),"Warranty. For life.",12);
        Reporter.log("Additional details are displayed");
        Assert.assertTrue(result, "Product features and reasons to buy are displayed as expected");
    }

    @Test(description = "Verify user is able to view reviews, ratings and frequently asked questions of the product")
    public void verifyProductReviewsRatingAndFAQs() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        result = productListingPage.verifyProductRatingDisplayed("4");
        Reporter.log("Product rating is displayed as >= 4");
        result = result && productListingPage.verifyNavigationToSection("Review","FAQS");
        Reporter.log("Review and FAQ sections are displayed");
        Assert.assertTrue(result, "Product reviews, ratings and frequently asked questions are displayed as expected");
    }

    @Test(description = "Verify user is able to view and select multiple slide images and videos of the product")
    public void verifyImageAndVideoSlidesOnSelection() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        String previousImage = productListingPage.getCurrentlyDisplayedImageSource();
        result = productListingPage.verifyProductImagesDisplayed(10);
        Reporter.log("Product images/video slides are displayed");
        Assert.assertTrue(!previousImage.equals(productListingPage.getCurrentlyDisplayedImageSource()), "User is able to view multiple images and videos of the product");
    }


    @Test(description = "Verify user is able to add multiple products to the cart for future purchase")
    public void verifyMultipleProductsCanBeAddedToCart() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        productListingPage.clickOnButton("Add to Cart");
        productListingPage.closeSubscriptionPopUp();
        Reporter.log("Product has been added to cart first time with items in cart count: 1" );
        int count = Integer.valueOf(productListingPage.getCartCount());
        productListingPage.clickOnButton("Add to Cart");
        Reporter.log("Product has been added to cart second time");
        Assert.assertTrue(Integer.valueOf(productListingPage.getCartCount())>=count*2, "User is able to add multiple products to cart for future purchase");
    }

    @Test(description = "Verify user is able to guest checkout using buy now option")
    public void verifyProductCanBeCheckedOutUsingBuyNow() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        productListingPage.clickOnButton("Buy Now");
        Reporter.log("Product has been checked out using Buy Now option");
        checkoutPage.enterDetails(properties.getProperty("username"));
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"), "User is able to guest checkout using buy now option");
    }

    @Test(description = "Verify twin mattress is selected by default and verify its specifications")
    public void verifyDefaultSelectedProductSpecs() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        result = productListingPage.verifyModelSelectedIs("twin");
        Reporter.log("Default model Twin is selected");
        result = result && productListingPage.verifySelectedModelSize("38\"W x 75\"L x 12\"H");
        Reporter.log("Verified default model: Twin specifications");
        Assert.assertTrue(result, "Twin mattress is selected by default");
    }

    @Test(dataProvider = "getModelsAndPricing",description = "Verify user is able to select and view different sizes and their specs")
    public void verifyDifferentSizesOfSameProductCanBeSelected(String model, String modelName, String size, String price) throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        productListingPage.clickOnModel(model);
        Reporter.log("Clicked on model: " + model);
        result = productListingPage.verifyModelSelectedIs(modelName);
        result = result && productListingPage.verifySelectedModelSize(size);
        Reporter.log("Product size is displayed as: " + size);
        result = result && productListingPage.getSelectedModelPrice().contains(price);
        Reporter.log("Product price is displayed as: " + price);
        Assert.assertTrue(result, "User is able to select and view different sizes and their specs");
    }

    @Test(description = "Verify delivery status and verify expected shipment date is not displayed in past")
    public void verifyDeliveryStatusAndEstimatedDeliveryDate() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        String deliveryDate = productListingPage.getSelectedModelShippingEstimate();
        result = (deliveryDate.split(" ")[0].equals(getCurrentAndFutureMonth().split(" ")[0]) || deliveryDate.split(" ")[0].equals(getCurrentAndFutureMonth().split(" ")[1]));
        Reporter.log("Estimated month of delivery date is:" + deliveryDate.split(" ")[0]);
        result = result && productListingPage.verifyShipmentStatus("Ready to Ship");
        Reporter.log("Delivery status is Ready to ship");
        Assert.assertTrue(result, "Expected shipment date is not displayed in past");
    }

    @Test(description = "Verify user is able to proceed to checkout screen using the add cart option")
    public void verifyCheckoutFromProductsAddedToCart() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        productListingPage.clickOnButton("Add to Cart");
        productListingPage.closeSubscriptionPopUp();
        productListingPage.clickOnCartAndCheckout();
        Reporter.log("Clicked on items added to cart");
        checkoutPage.enterDetails(properties.getProperty("username"));
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"), "User is able to guest checkout using buy now option");
    }

    @Test(description = "Verify free addons and price of the selected product is displayed as the same in checkout screen")
    public void verifyFreeAddonsAndPricePassedFromListingToCheckoutScreen() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        productListingPage.clickOnModel("Queen");
        Reporter.log("Clicked on model: " + "Queen");
        String price = productListingPage.getSelectedModelPrice();
        int freeItemsCount = productListingPage.getFreeBundleForSelectedProduct();
        Reporter.log("Model price: " + price);
        productListingPage.clickOnButton("Buy Now");
        Reporter.log("Navigated to checkout page");
        checkoutPage.enterDetails(properties.getProperty("username"));
        result = checkoutPage.getCheckoutPrice().equals(price);
        result = result && (checkoutPage.getTotalItemsCount()== freeItemsCount+1);
        Reporter.log("Checked out screen is displayed");
        Assert.assertTrue(result, "Free addons count and price of the selected product is displayed as the same in checkout screen");
    }

    @Test(description = "Verify item name and quantity of the selected product is displayed as the same in checkout screen")
    public void verifyItemNameAndQuantityPassedFromListingToCheckoutScreen() throws InterruptedException {
        productListingPage.navigateTo(properties.getProperty("url"));
        Reporter.log("Navigated to: " + properties.getProperty("url"));
        productListingPage.clickOnButton("Add to Cart");
        productListingPage.closeSubscriptionPopUp();
        productListingPage.clickOnModel("Queen");
        productListingPage.clickOnButton("Add to Cart");
        Reporter.log("Clicked on model: " + "Queen " + "and added to cart");
        productListingPage.clickOnCheckout();
        checkoutPage.enterDetails(properties.getProperty("username"));
        result = checkoutPage.verifyItemDetailsOnCheckoutScreen("1","Queen","$2,249");
        result = result && checkoutPage.verifyItemDetailsOnCheckoutScreen("2","White / Standard","Free");
        result = result && checkoutPage.verifyItemDetailsOnCheckoutScreen("1","Queen","Free");
        result = result && checkoutPage.verifyItemDetailsOnCheckoutScreen("1","Gray / Queen","Free");
        Reporter.log("Items are displayed in checkout screen");
        Assert.assertTrue(result, "Item name and quantity of the selected product is displayed as the same in checkout screen");
    }

    public String getCurrentAndFutureMonth() {
        String month;
        Calendar c = Calendar.getInstance();
        c.get(Calendar.DAY_OF_MONTH);
        month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
        month = month + " " + c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        return month;
    }

    @DataProvider (name = "getModelsAndPricing")
    public Object[][] getModelsAndPricing(){
        return new Object[][] {{"Twin XL","twin-xl","38\"W x 80\"L x 12\"H","$1,799"},
                {"Full","full","54\"W x 75\"L x 12\"H","$2,049"},
                {"Queen","queen","60\"W x 80\"L x 12\"H","$2,249"},
                {"King","king","76\"W x 80\"L x 12\"H","$2,449"},
                {"Cal King","cal-king","72\"W x 84\"L x 12\"H","$2,449"},
                {"Split King","split-king","2 - 38\"W x 80\"L x 12\"H","$3,598"}};
    }

    @AfterClass
    public void setUpClose() {
        if(driver!=null)
            driver.close();
    }
}