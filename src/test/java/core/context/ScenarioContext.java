package core.context;

public class ScenarioContext {

    private String scenarioId;
    private String scenarioName;
    private String browser;
    private long threadId;
    private String sessionId;
    private String status;

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
