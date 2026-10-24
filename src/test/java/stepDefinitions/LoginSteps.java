
package stepDefinitions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;

import core.context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.components.HeaderComponent;
import utils.EnvReader;

public class LoginSteps {

    private final ScenarioContext scenarioContext;

    private LoginPage loginPage;
    private HeaderComponent header;

    public LoginSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    private LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(scenarioContext.getDriver());
        }
        return loginPage;
    }

    private HeaderComponent header() {
        if (header == null) {
            header = new HeaderComponent(scenarioContext.getDriver());
        }
        return header;
    }

    @Given("User logs in with valid credentials username {string} and password {string}")
    public void user_logs_in_with_valid_credentials_username_and_password(
            String usernameString,
            String passwordString) {

        String username = resolveCredential(usernameString);
        String password = resolveCredential(passwordString);

        loginPage().open();
        loginPage().login(username, password);
        loginPage().clickSigninBtn();
    }

    @Given("the user is in the MediShop login page")
    public void the_user_is_in_login_page() {

        loginPage().open();

        assertThat(loginPage().isLoginPage())
                .as("User should be on the login page")
                .isTrue();
    }

    @When("user enters credentials username {string} and password {string}")
    public void user_enters_credentials_username_and_password(
            String usernameString,
            String passwordString) {

        String username = resolveCredential(usernameString);
        String password = resolveCredential(passwordString);

        loginPage().login(username, password);
    }

    @When("clicks the Sign In button")
    public void click_sign_in_button() {
        loginPage().clickSigninBtn();
    }

    @Then("the login result should be {string}")
    public void the_login_result_should_be(String result) {

        switch (result.toLowerCase()) {

            case "success":
                assertThat(header().isAccountMenuDisplayed())
                        .as("User should be logged in successfully")
                        .isTrue();
                break;

            case "error":
                SoftAssertions softAssert = new SoftAssertions();

                softAssert.assertThat(loginPage().isAlertDisplayed())
                        .as("Login alert should be displayed")
                        .isTrue();

                softAssert.assertThat(loginPage().getLoginAlertMessage())
                        .as("Login alert message")
                        .isEqualTo(
                                "Invalid email or password. Please try again."
                        );

                softAssert.assertThat(
                        loginPage().isinvalidErrorMessageDisplayed()
                )
                        .as("Invalid credentials message should be displayed")
                        .isTrue();

                softAssert.assertThat(loginPage().getInvalidErrorMessage())
                        .as("Invalid credentials message")
                        .isEqualTo("Invalid credentials");

                softAssert.assertAll();
                break;

            case "invalidemailalert":
                assertThat(loginPage().getInvalidEmailMessage())
                        .as("Invalid email alert message")
                        .isEqualTo("Enter a valid email address");
                break;

            case "usernamerequired":
                assertThat(loginPage().getInvalidEmailMessage())
                        .as("Required email validation message")
                        .isEqualTo("Email is required");
                break;

            case "passwordrequired":
                assertThat(loginPage().getInvalidErrorMessage())
                        .as("Required password validation message")
                        .isEqualTo("Password is required");
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown login result: " + result
                );
        }
    }

    private String resolveCredential(String value) {

        if (value.equalsIgnoreCase("${medishop_username}")) {
            return EnvReader.get("medishop_username");
        }

        if (value.equalsIgnoreCase("${medishop_password}")) {
            return EnvReader.get("medishop_password");
        }

        return value;
    }
}
