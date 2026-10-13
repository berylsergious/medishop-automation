package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{

    private static final String ROUTE = "/login.html";


    public LoginPage (WebDriver driver){
        super(driver);
    }

    
    //Locators
    private By usernameField = By.xpath("//input[@id='email_id']");
    private By passwordField = By.xpath("//input[@id='password_id']");
    private By signInBtn = By.xpath("//button[@id='signin_button']");

    private By login_alert_message = By.xpath("//span[@data-testid='login_alert_message']");
    private By invalidErrorMessage = By.xpath("//p[@id='password_id_error']/span");
    private By invalidEmailAlert = By.xpath("//p[@id='email_id_error']/span");






    //Actions
    public void login(String username, String password){
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickSigninBtn (){
        waitForElementClickable(signInBtn);
        driver.findElement(signInBtn).click();
    }

    public void open() {
        driver.get(BASE_URL + ROUTE);
    }

    

    public boolean isLoginPage(){
        waitForElementVisible(usernameField);
         return driver.findElement(usernameField).isDisplayed();
    }

    public boolean isAlertDisplayed(){
        waitForElementVisible(login_alert_message);
        return driver.findElement(login_alert_message).isDisplayed();
    }

    public String getLoginAlertMessage(){
        waitForElementVisible(login_alert_message);
        return driver.findElement(login_alert_message).getText();
    }

    public boolean isinvalidErrorMessageDisplayed(){
        waitForElementVisible(invalidErrorMessage);
        return driver.findElement(invalidErrorMessage).isDisplayed();
    }

    public String getInvalidErrorMessage(){
        waitForElementVisible(invalidErrorMessage);
        return driver.findElement(invalidErrorMessage).getText();
    }

    public String getInvalidEmailMessage(){
        waitForElementVisible(invalidEmailAlert);
        return driver.findElement(invalidEmailAlert).getText();
    }



}
