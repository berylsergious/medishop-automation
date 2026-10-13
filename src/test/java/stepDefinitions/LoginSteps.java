package stepDefinitions;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.components.HeaderComponent;
import utils.EnvReader;

public class LoginSteps {

     private LoginPage loginPage;
     private HeaderComponent header;

    @Given("the user is in the MediShop login page")
    public void the_user_is_in_login_page() {

        loginPage = new LoginPage(Hooks.getDriver());
        loginPage.open();
        assertThat(loginPage.isLoginPage())
        .as("user is in login page")
        .isTrue();
    }


    @When("user enters credentials username {string} and password {string}")
    public void user_enters_credentials_username_and_password(String usernameString, String passwordString) {

        String username = resolveCredential(usernameString);
        String password = resolveCredential(passwordString); 

        loginPage.login(username, password);

    }


    @When("clicks the Sign In button")
    public void click_SignIn_button() {
        loginPage.clickSigninBtn();
       
    }


    @Then("the login result should be {string}")
    public void the_login_result_should_be(String result) {
        

        switch (result.toLowerCase()) {
            case "success":
                header = new HeaderComponent(Hooks.getDriver());
                assertThat(header.isAccountMenuDisplayed())
                .as("user logged in successfully")
                .isTrue();
                break;

            case "error":

                SoftAssertions softAssert = new SoftAssertions();

                softAssert.assertThat(loginPage.isAlertDisplayed())
                .as("user can see the login alert message ????")
                .isTrue();

                softAssert.assertThat(loginPage.getLoginAlertMessage())
                .as("login alert message is as expected ????")
                .isEqualTo("Invalid email or password. Please try again.");

                softAssert.assertThat(loginPage.isinvalidErrorMessageDisplayed())
                .as("user can see the invalid credentials alert message ????")
                .isTrue();

                softAssert.assertThat(loginPage.getInvalidErrorMessage()).
                as("invalid credentials alert message is as expected ????")
                .isEqualTo("Invalid credentials");
                softAssert.assertAll();
                break;

            case "invalidemailalert":
                assertThat(loginPage.getInvalidEmailMessage())
                .as("user can see the correct invalid email alert text ????")
                .isEqualTo("Enter a valid email address");
                break;

            case "usernamerequired":
                 assertThat(loginPage.getInvalidEmailMessage())
                .as("user can see the correct invalid email alert text ????")
                .isEqualTo("Email is required");
                break;

            case "passwordrequired":
                 assertThat(loginPage.getInvalidErrorMessage())
                .as("user can see the correct invalid password alert text ????")
                .isEqualTo("Password is required");
                break;

            default:

            throw new IllegalArgumentException(
                    "Unknown login result: " + result
            );
    
        }
        
        
    }

   


    private String resolveCredential(String value){
        
        
        if (value.equalsIgnoreCase("${medishop_username}")){
        return EnvReader.get("medishop_username");
        }

        if (value.equalsIgnoreCase("${medishop_password}")){
        return EnvReader.get("medishop_password");
        }
        
        return value;
    }

    
}
