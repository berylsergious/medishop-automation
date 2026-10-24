
package core.context;

import org.openqa.selenium.WebDriver;

public class ScenarioContext {

    private String scenarioId;
    private String scenarioName;
    private String browser;
    private long threadId;
    private String sessionId;
    private String status;

    private WebDriver driver;

    public void startScenario(
            String scenarioId,
            String scenarioName,
            String browser,
            long threadId) {

        this.scenarioId = scenarioId;
        this.scenarioName = scenarioName;
        this.browser = browser;
        this.threadId = threadId;
    }

    public void setDriver(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(
                    "WebDriver must not be null.");
        }

        this.driver = driver;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver has not been initialized for this scenario.");
        }

        return driver;
    }

    public void clearDriver() {
        this.driver = null;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public String getBrowser() {
        return browser;
    }

    public long getThreadId() {
        return threadId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
