package com.fedii.tools;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
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

    static WebDriver driver;

    public static WebDriver getDriver(String browser) {
        DesiredCapabilities capabilities;
        boolean isLocal = false;
        switch (browser) {
            case "firefox":

                capabilities = DesiredCapabilities.firefox();
                break;
            case "chrome":
                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                break;
            default:
                driver = new FirefoxDriver();
                capabilities = null;
                isLocal = true;
                break;
        }

        if (!isLocal) {
            driver = new RemoteWebDriver(getGridHubUrl(), capabilities);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        }

        return driver;
    }

    public static WebDriver getDriver() {
        return getDriver("firefox");

    }

    private static URL getGridHubUrl() {
        URL hub = null;
        try {
            hub = new URL(Config.getProperty("gridhub"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return hub;
    }
}
