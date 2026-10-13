
package pages.components;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeaderComponent {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By homeLogoBtn =
            By.cssSelector(".site-header a.brand");

    private final By searchBox =
            By.xpath("//form/input[contains(@placeholder,'Search medicines, symptoms...')]");

    private final By homeMenu =
            By.xpath("//nav/a[contains(text(),'Home')]");

    private final By shopMenu =
            By.xpath("//nav/a[contains(text(),'Shop')]");

    private final By ordersMenu =
            By.xpath("//nav/a[contains(text(),'Orders')]");

    private final By wellnessMenu =
            By.xpath("//nav/a[contains(text(),'Wellness')]");

    private final By cartMenu =
            By.xpath("//div/a[@class='icon-btn']");

    private final By cartCount =
            By.cssSelector("span.cart-badge");

    private final By accountMenu =
            By.xpath("//div/a[@class='icon-btn']/following-sibling::div/child::button");

    private final By accountMenuProfile =
            By.cssSelector("#account_menu [data-testid='menu_profile']");

    private final By accountMenuOrders =
            By.cssSelector("#account_menu [data-testid='menu_orders']");

    private final By accountMenuAddresses =
            By.cssSelector("#account_menu [data-testid='menu_addresses']");

    private final By accountMenuSettings =
            By.cssSelector("#account_menu [data-testid='menu_settings']");

    private final By accountMenuLogout =
            By.cssSelector("#account_menu [data-testid='logout_button']");

    public HeaderComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));
    }

    private WebElement visible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement clickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean isAccountMenuDisplayed() {
        return visible(accountMenu).isDisplayed();
    }

    public void search(String input) {
        WebElement search = visible(searchBox);
        search.clear();
        search.sendKeys(input, Keys.ENTER);
    }

    public int getCartItemQuantity() {
        // A badge may be absent when the cart is empty.
        if (driver.findElements(cartCount).isEmpty()) {
            return 0;
        }

        String count = visible(cartCount).getText();
        return Integer.parseInt(count);
    }

    public void goToCart() {
        clickable(cartMenu).click();
    }

    public void goToHomeLogo() {
        clickable(homeLogoBtn).click();
    }

    public void goToHome() {
        clickable(homeMenu).click();
    }

    public void goToShop() {
        clickable(shopMenu).click();
    }

    public void goToOrders() {
        clickable(ordersMenu).click();
    }

    public void goToWellness() {
        clickable(wellnessMenu).click();
    }

    public void goToProfile() {
        clickable(accountMenu).click();
        clickable(accountMenuProfile).click();
    }

    public void goToOrdersFromAccountMenu() {
        clickable(accountMenu).click();
        clickable(accountMenuOrders).click();
    }

    public void goToAddress() {
        clickable(accountMenu).click();
        clickable(accountMenuAddresses).click();
    }

    public void goToSettings() {
        clickable(accountMenu).click();
        clickable(accountMenuSettings).click();
    }

    public void accountLogout() {
        clickable(accountMenu).click();
        clickable(accountMenuLogout).click();
    }
}
