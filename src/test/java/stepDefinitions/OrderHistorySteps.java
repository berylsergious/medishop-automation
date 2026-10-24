package stepDefinitions;


import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebElement;

import core.context.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.OrdersPage;


public class OrderHistorySteps {


    private final ScenarioContext scenarioContext;
    private OrdersPage ordersPage;

    public OrderHistorySteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    private OrdersPage ordersPage() {
        if (ordersPage == null) {
            ordersPage = new OrdersPage(scenarioContext.getDriver());
        }
        return ordersPage;
    }

  @Then("the orders page should be displayed")
    public void user_is_in_the_orderspage() {
        assertThat(ordersPage().isOrdersPage())
                .as("Orders page should be displayed")
                .isTrue();
    }

    @And("the page header should be {string}")
    public void the_page_header_should_be(String expectedHeader) {
        String actualHeader = ordersPage().getHeaderText();

        assertThat(actualHeader)
                .as("Orders page header")
                .isEqualTo(expectedHeader);
    }

    @And("the page subtitle should be {string}")
    public void the_page_subtitle_should_be(String expectedSubtitle) {
        String actualSubtitle = ordersPage().getSubtitleText();

        assertThat(actualSubtitle)
                .as("Orders page subtitle")
                .isEqualTo(expectedSubtitle);
    }

    @And("the {string} dropdown should display {string}")
    public void the_dropdown_should_display(String dropdownName,String expectedValue) {

        if ("Filter by status".equals(dropdownName)) {
            String actualValue = ordersPage().getSelectedFilterValue();

            assertThat(actualValue)
                    .as("Default value for dropdown: " + dropdownName)
                    .isEqualTo(expectedValue);
        } else {
            throw new IllegalArgumentException(
                    "Dropdown not supported in this step: " + dropdownName
            );
        }
    }

    @And("the {string} input field should be empty")
    public void the_input_field_should_be_empty(String fieldName) {

        if ("Search orders".equals(fieldName)) {
            String actualValue = ordersPage().getSearchInputValue();

            assertThat(actualValue)
                    .as("Field '" + fieldName + "' should be empty")
                    .isEmpty();
        } else {
            throw new IllegalArgumentException(
                    "Input field not supported in this step: " + fieldName
            );
        }
    }

    @When("the user enters {string} into the {string} field")
    public void userSearchForOrders(String productName, String fieldName) {

        if ("Search orders".equalsIgnoreCase(fieldName)) {
            ordersPage().enterSearchTerm(productName);
        } else {
            throw new IllegalArgumentException(
                    "Unsupported field in this step: " + fieldName
            );
        }
    }

    @Then("the user should see the {string} button")
    public void the_user_should_see_the_button(String expectedButtonText) {
        String actualButtonText = ordersPage().getButtonText();

        assertThat(actualButtonText)
                .as("Button text")
                .isEqualTo(expectedButtonText);
    }


    @Then ("a {string} and {string} message should be displayed")
    public void a_message_should_be_displayed(String expectedMessage, String expectedErrorSubtitle) {
        String actualMessage = ordersPage().getNoResultMessage();
        String actualErrorSubtitle = ordersPage().getNoOrdersSubtitleMessage();

        assertThat(actualMessage)
                .as("No-results message")
                .isEqualTo(expectedMessage);

        assertThat(actualErrorSubtitle)
                .as("no-Orders SubtitleMessage")
                .isEqualTo(expectedErrorSubtitle);
    }

    @Then("the order count summary should display {string}")
    public void the_order_count_summary_should_display(String expectedCountString) {

        // 1. Wait for the UI to update
        ordersPage.waitForResultCountToUpdate(expectedCountString);

        String actualCountString = ordersPage().getResultCountString();
        assertThat(actualCountString)
                .as("results Count message")
                .isEqualTo(expectedCountString);
    }

    @And ("the order card for {string} should be displayed in right format")
    public void the_order_card_for_should_be_displayed_in_right_format(String expectedOrderID) {
        
        // 1. Get the specific card directly
        WebElement orderCard = ordersPage.getOrderCardById(expectedOrderID);

        // 2. Get the text of the card, and extract ONLY the order ID part.
        String actualOrderId = orderCard.getText().split("\n")[0].trim();


        // 1. Check the format matches the regex
        String orderIdRegex = "(?i)^ORDER #[A-Z]{2}-\\d{4}-[A-Z]{2}$";
        assertThat(actualOrderId)
                .as("Check for Order ID format")
                .matches(orderIdRegex);

        // 2. Check it matches the expected ID
        assertThat(actualOrderId)
                .as("Check for Order ID match")
                .contains(expectedOrderID);

    }
}