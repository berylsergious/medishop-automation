
package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.components.HeaderComponent;
import utils.ConfigReader;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final HeaderComponent header;

    protected static final String BASE_URL =
            ConfigReader.get("base.url");

    public BasePage(WebDriver driver) {
        this.driver = driver;

        int timeout = Integer.parseInt(
                ConfigReader.get("timeout"));

        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(timeout));

        this.header = new HeaderComponent(driver);
    }

    protected void waitForPageLoad() {
        wait.until(d ->
                "complete".equals(
                        ((JavascriptExecutor) d)
                                .executeScript("return document.readyState")));
    }

    protected WebElement waitForElementVisible(By locator) {
       return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
