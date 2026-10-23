
package stepDefinitions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import core.context.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import pages.components.HeaderComponent;

public class HomePageSteps {

    private final ScenarioContext scenarioContext;
    private HeaderComponent header;

    public HomePageSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    private HeaderComponent header() {
        if (header == null) {
            header = new HeaderComponent(scenarioContext.getDriver());
        }
        return header;
    }

    @Given("user is in the homepage")
    public void user_is_in_the_homepage() {

        assertThat(header().isAccountMenuDisplayed())
                .as("Account menu should be displayed on the homepage")
                .isTrue();
    }

    @And("the user navigates to the {string} page")
    public void navigateToPage(String pageName) {

        switch (pageName.trim().toLowerCase(Locale.ROOT)) {
            case "orders" -> header().goToOrders();
            case "cart" -> header().goToCart();
            case "home" -> header().goToHome();
            case "shop" -> header().goToShop();
            case "wellness" -> header().goToWellness();

            default -> throw new IllegalArgumentException(
                    "Unsupported navigation page: " + pageName
            );
        }
    }
}
