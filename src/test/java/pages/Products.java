package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class Products extends BasePage{

    //Locators
    private By SearchInputBox = By.cssSelector("input#product_search_input");

    private By addToCartButton(String productName){
        return By.xpath(String.format("//a[contains(., '%s')]"+
                        "/ancestor::div[contains(@class, 'product-body')]"+
                        "/following-sibling::div[contains(@class, 'product-foot')]"+
                        "/button",productName));
    }

    public Products(WebDriver driver) {
        super(driver);
    }


    //actions
    public void clickAddToCart (String product){
        waitForElementClickable(addToCartButton(product));
        driver.findElement(addToCartButton(product)).click();
    }

    public boolean isSearchBoxDisplayed(){
        try {
            waitForElementVisible(SearchInputBox);
            return driver.findElement(SearchInputBox).isDisplayed();
        } catch (TimeoutException e) {
        return false;
    }
}
    
}
