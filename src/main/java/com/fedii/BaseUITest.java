package com.fedii;

import com.fedii.tools.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

/**
 * Created by sfedii on 3/22/16.
 */
public abstract class BaseUITest implements ITest {
    protected WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(String browser) {
        driver = DriverFactory.getDriver(browser);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public String getTestName() {
        return "Put test name here";
    }
}
