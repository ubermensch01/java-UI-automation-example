package com.fedii.tools;

import io.github.bonigarcia.wdm.BrowserManager;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by sfedii on 2/29/16.
 */
public class DriverFactory {

    private DriverFactory() {
    }

    private static DriverFactory instance = new DriverFactory();

    public static DriverFactory getInstance() {
        return instance;
    }

    public WebDriver getDriver(String browser) {
        DesiredCapabilities capabilities;

        if (browser.equals("local-ff")) {
            setupGecko();
            capabilities = DesiredCapabilities.firefox();
            return new FirefoxDriver(capabilities);
        }

        if (browser.equals("local-chrome")) {
            setupChrome();
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
            return new ChromeDriver(capabilities);
        }

        switch (browser) {
            case "firefox":
            default:
                setupGecko();
                capabilities = DesiredCapabilities.firefox();
                break;
            case "chrome":
                setupChrome();
                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                break;
        }

        WebDriver driver = new RemoteWebDriver(getGridHubUrl(), capabilities);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

        return driver;
    }

    public WebDriver getDriver() {
        return getDriver("local-ff");
    }


    private void setupGecko() {
        BrowserManager driverManager = FirefoxDriverManager.getInstance();
        driverManager.setup();
        System.out.println(String.format("FF driver version : %s", driverManager.getDownloadedVersion()));
    }

    private void setupChrome() {
        BrowserManager driverManager = ChromeDriverManager.getInstance();
        driverManager.setup();
        System.out.println(String.format("Chrome driver version : %s", driverManager.getDownloadedVersion()));
    }

    private URL getGridHubUrl() {
        URL hub = null;
        try {
            hub = new URL(Config.getInstance().getProperty("gridhub"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return hub;
    }
}
