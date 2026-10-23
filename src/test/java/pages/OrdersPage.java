package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrdersPage extends BasePage{


    // Locators
    private By pageHeader = By.cssSelector(".page-head h1");
    private By pageSubtitle = By.cssSelector(".page-head p");


    private By searchOrders = By.xpath("//input[@placeholder='Order ID or product name']");
    private By filterDropdown = By.xpath("//select");
    private By defaultDropdownValue = By.xpath("//select/option[contains(text(), 'All')]");
    private By resetButton = By.xpath("//button[text()='Reset']");
    private By noOrdersMessage = By.cssSelector("#orders_empty h3");
    private By noOrdersSubtitleMessage = By.cssSelector("#orders_empty p");
    private By resultCountText  = By.cssSelector("#orders_count");

    public WebElement getOrderCardById( String expectedOrderId){

        String cleanId = expectedOrderId.replace("#", "").trim();
        By specificCard =By.xpath("//article[@data-order-id='"+cleanId+"']");
        return waitForElementVisible(specificCard);
    }


    public OrdersPage (WebDriver driver){
        super(driver);
    }

    //actions
    public boolean isOrdersPage(){
         return waitForElementVisible(pageHeader).isDisplayed() && waitForElementVisible(pageSubtitle).isDisplayed();
    }

    public String getHeaderText(){
     return waitForElementVisible(pageHeader).getText();
    }

    public String getSubtitleText(){
    return waitForElementVisible(pageSubtitle).getText();
    }

    public String getSelectedFilterValue (){
        String defaultOption = waitForElementVisible(defaultDropdownValue).getText();
        return defaultOption;
    }

    public String getSearchInputValue (){
        String defaultSearch = waitForElementVisible(searchOrders).getText();
        return defaultSearch;
    }

    public void enterSearchTerm(String searchTerm) {

    WebElement searchField = waitForElementVisible(searchOrders);
    searchField.clear();
    searchField.sendKeys(searchTerm);

}

    public String getButtonText(){
        return waitForElementVisible(resetButton).getText();
    }
    
    public String getNoResultMessage(){
        return waitForElementVisible(noOrdersMessage).getText();
    }

    public String getNoOrdersSubtitleMessage(){
        return waitForElementVisible(noOrdersSubtitleMessage).getText();
    }

    public String getResultCountString(){
        return waitForElementVisible(resultCountText ).getText().trim();
    }

    public void waitForResultCountToUpdate(String expectedCountString){
        wait.until(ExpectedConditions.textToBePresentInElementLocated(resultCountText ,expectedCountString));
    }


}
