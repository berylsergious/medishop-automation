package hooks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import core.context.ScenarioContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.cucumber.java.Scenario;
import core.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.ConfigReader;

public class Hooks {

    private static final ThreadLocal<WebDriver> DRIVER =
            new ThreadLocal<>();

    private final ScenarioContext scenarioContext;

    public Hooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }
    

    @Before
    public void setUp(Scenario scenario) {

        String executionMode = ConfigReader.get("execution.mode");
        String browser = ConfigReader.get("browser");
        String gridUrl = ConfigReader.get("grid.url");

        WebDriver webDriver = DriverFactory.createDriver(
                executionMode,
                browser,
                gridUrl
                );

        DRIVER.set(webDriver);

        scenarioContext.startScenario(
                scenario.getId(),
                scenario.getName(),
                browser,
                Thread.currentThread().threadId()
        );

        webDriver.manage().window().maximize();
        String sessionId =((RemoteWebDriver) webDriver).getSessionId().toString();

        scenarioContext.setSessionId(sessionId);

        System.out.println("\n========== SCENARIO STARTED ==========");
        System.out.println("Scenario ID : " + scenarioContext.getScenarioId());
        System.out.println("Scenario    : " + scenarioContext.getScenarioName());
        System.out.println("Browser     : " + scenarioContext.getBrowser());
        System.out.println("Thread      : " + scenarioContext.getThreadId());
        System.out.println("Session ID  : " + scenarioContext.getSessionId());
        System.out.println("======================================\n");
    }

    public static WebDriver getDriver() {

        WebDriver webDriver = DRIVER.get();

        if (webDriver == null) {
            throw new IllegalStateException(
                    "WebDriver is not initialized for this thread.");
        }

        return webDriver;
    }

    @After
    public void tearDown(Scenario scenario) {

        scenarioContext.setStatus(scenario.getStatus().name());
        System.out.println("\n========== SCENARIO FINISHED ==========");
        System.out.println("Scenario    : " + scenarioContext.getScenarioName());
        System.out.println("Browser     : " + scenarioContext.getBrowser());
        System.out.println("Thread      : " + scenarioContext.getThreadId());
        System.out.println("Session ID  : " + scenarioContext.getSessionId());
        System.out.println("Status      : " + scenarioContext.getStatus());
        System.out.println("=======================================\n");

        WebDriver webDriver = DRIVER.get();

    
        try {
            // Attach scenario metadata to the Cucumber report
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

            // Capture a screenshot only when the scenario fails
            if (scenario.isFailed() && webDriver != null) {
                byte[] screenshot =
                    ((TakesScreenshot) webDriver)
                        .getScreenshotAs(OutputType.BYTES);

                scenario.attach(
                    screenshot,
                    "image/png",
                    "Failure screenshot"
                );
            }

        } catch (Exception e) {
            System.err.println(
                "Unable to attach scenario evidence: " + e.getMessage()
            );
        } finally {
            if (webDriver != null) {
                try {
                    webDriver.quit();
                } finally {
                    DRIVER.remove();
                }
            } else {
                DRIVER.remove();
            }
        }

    }
}