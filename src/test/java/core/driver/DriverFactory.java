package core.driver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Locale;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class DriverFactory {

    private DriverFactory() {
        // Utility class: prevent object creation.
    }

    public static WebDriver createDriver(
            String executionMode,
            String browser,
            String gridUrl) {

        if (executionMode == null || executionMode.isBlank()) {
            throw new IllegalArgumentException(
                    "Execution mode must not be empty.");
        }

        if (browser == null || browser.isBlank()) {
            throw new IllegalArgumentException(
                    "Browser configuration must not be empty.");
        }

        String mode = executionMode.trim().toLowerCase(Locale.ROOT);
        String browserName = browser.trim().toLowerCase(Locale.ROOT);

        if (mode.equals("local")) {
            return createLocalDriver(browserName);
        }

        if (mode.equals("remote")) {
            return createRemoteDriver(browserName, gridUrl);
        }

        throw new IllegalArgumentException(
                "Unsupported execution mode: " + mode
                + ". Supported modes: local, remote.");
    }

    private static WebDriver createLocalDriver(String browser) {

        switch (browser) {
            case "chrome":
                return new ChromeDriver();

            case "firefox":
                return new FirefoxDriver();

            case "edge":
                return new EdgeDriver();

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser);
        }
    }

    private static WebDriver createRemoteDriver(
            String browser,
            String gridUrl) {

        if (gridUrl == null || gridUrl.isBlank()) {
            throw new IllegalArgumentException(
                    "Grid URL is required for remote execution.");
        }

        try {
            URI uri = URI.create(gridUrl.trim());

            switch (browser) {
                case "chrome":
                    return new RemoteWebDriver(
                            uri.toURL(), new ChromeOptions());

                case "firefox":
                    return new RemoteWebDriver(
                            uri.toURL(), new FirefoxOptions());

                case "edge":
                    return new RemoteWebDriver(
                            uri.toURL(), new EdgeOptions());

                default:
                    throw new IllegalArgumentException(
                            "Unsupported browser: " + browser);
            }

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(
                    "Invalid Selenium Grid URL: " + gridUrl, e);
        }
    }
}