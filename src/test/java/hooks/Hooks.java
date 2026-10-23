package hooks;

import core.context.ScenarioContext;
import core.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.ConfigReader;

public class Hooks {

    private final ScenarioContext scenarioContext;
    private WebDriver webDriver;

    public Hooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Before
    public void setUp(Scenario scenario) {
        try {
            String executionMode = ConfigReader.get("execution.mode");
            String browser = ConfigReader.get("browser");
            String gridUrl = ConfigReader.get("grid.url");

            webDriver = DriverFactory.createDriver(
                    executionMode,
                    browser,
                    gridUrl
            );

            scenarioContext.startScenario(
                    scenario.getId(),
                    scenario.getName(),
                    browser,
                    Thread.currentThread().threadId()
            );
            scenarioContext.setDriver(webDriver);

            webDriver.manage().window().maximize();

            if (webDriver instanceof RemoteWebDriver remoteDriver) {
                scenarioContext.setSessionId(
                        remoteDriver.getSessionId().toString()
                );
            }
        } catch (RuntimeException | Error e) {
            System.err.println(
                    "Scenario setup failed: " + scenario.getName()
            );
            cleanupDriver(webDriver);
            throw e;
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            scenarioContext.setStatus(scenario.getStatus().name());

            // Keep evidence operations independent so one failure does not
            // prevent the other diagnostic from being attached.
            attachScenarioMetadata(scenario);

            if (scenario.isFailed()) {
                attachFailureScreenshot(scenario, webDriver);
            }
        } catch (RuntimeException | Error e) {
            // Diagnostic errors must not replace the scenario outcome.
            System.err.println(
                    "Unexpected teardown diagnostic error: " + e.getMessage()
            );
        } finally {
            cleanupDriver(webDriver);
        }
    }

    private void attachScenarioMetadata(Scenario scenario) {
        try {
            String metadata = """
                    Scenario ID: %s
                    Scenario: %s
                    Browser: %s
                    Thread: %d
                    Session ID: %s
                    Status: %s
                    """.formatted(
                    scenarioContext.getScenarioId(),
                    scenarioContext.getScenarioName(),
                    scenarioContext.getBrowser(),
                    scenarioContext.getThreadId(),
                    scenarioContext.getSessionId(),
                    scenarioContext.getStatus()
            );

            scenario.attach(
                    metadata,
                    "text/plain",
                    "Scenario metadata"
            );
        } catch (RuntimeException e) {
            System.err.println(
                    "Unable to attach scenario metadata: " + e.getMessage()
            );
        }
    }

    private void attachFailureScreenshot(
            Scenario scenario,
            WebDriver driver) {

        if (driver == null) {
            System.err.println(
                    "Screenshot skipped: WebDriver is unavailable."
            );
            return;
        }

        try {
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            scenario.attach(
                    screenshot,
                    "image/png",
                    "Failure screenshot"
            );
        } catch (RuntimeException e) {
            System.err.println(
                    "Unable to capture or attach failure screenshot: "
                            + e.getMessage()
            );
        }
    }

    private void cleanupDriver(WebDriver driver) {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (RuntimeException e) {
            System.err.println(
                    "Unable to close WebDriver cleanly: " + e.getMessage()
            );
        } finally {
            scenarioContext.clearDriver();
            webDriver = null;
        }
    }
}