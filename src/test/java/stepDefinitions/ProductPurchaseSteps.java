package stepDefinitions;



import hooks.Hooks;
import io.cucumber.java.en.Given;
import pages.LoginPage;
import pages.components.HeaderComponent;
import utils.EnvReader;

public class ProductPurchaseSteps {

     private LoginPage loginPage;
     private HeaderComponent header;

 @Given("User logs in with valid credentials username {string} and password {string}")
    public void User_logs_in_with_valid_credentials_username_and_password(String usernameString, String passwordString) {
        String username = resolveCredential(usernameString);
        String password = resolveCredential(passwordString); 

        loginPage = new LoginPage(Hooks.getDriver());
        loginPage.login(username, password);
        loginPage.clickSigninBtn();
    }


    @Given("user is in the homepage")
    public void user_is_in_the_homepage() {
        
        header = new HeaderComponent(Hooks.getDriver());
        header.isAccountMenuDisplayed();
    }
  




    public String resolveCredential (String creds){
        
        switch (creds) {
            case "${medishop_username}":
                return EnvReader.get("medishop_username");
                
            case "${medishop_password}":
                return EnvReader.get("medishop_password");
            default:
                return creds;
        }
    }
    
}
