package com.fedii.pages;

import com.fedii.tools.Config;
import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sfedii on 2/29/16.
 */
public class BasePage {
    protected WebDriver driver;
    private Logger logger = Logger.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement find(By locator) {
        logger.info(String.format("Locating element '%s'", locator.toString()));

        return new FluentWait<>(driver)
                .withTimeout(Config.LOCATION_TIMEOUT, TimeUnit.MILLISECONDS)
                .pollingEvery(Config.POLLING_PERIOD, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(new Function<WebDriver, WebElement>() {
                    @Override
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(locator);
                    }
                });
    }

    public List<WebElement> findList(By locator) {
        logger.info(String.format("Locating element '%s'", locator.toString()));

        return driver.findElements(locator);
    }

    public WebElement findIn(WebElement element,
                             By locator) {
        logger.info(String.format("Locating element '%s' in element %s", locator.toString(), element.toString()));

        return new FluentWait<>(element)
                .withTimeout(Config.LOCATION_TIMEOUT, TimeUnit.MILLISECONDS)
                .pollingEvery(Config.POLLING_PERIOD, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(new Function<WebElement, WebElement>() {
                    public WebElement apply(WebElement webElement) {
                        return webElement.findElement(locator);
                    }
                });
    }

    public void click(By locator) {
        logger.info(String.format("Clicking element '%s'", locator.toString()));
        find(locator).click();
    }

    public void type(String text,
                     By locator) {
        type(text, true, locator);
    }

    public void type(String text,
                     boolean append,
                     By locator) {
        WebElement element = find(locator);
        if (append) {
            element.clear();
        }

        logger.info(String.format("Typing '%s' in element '%s'", text, locator.toString()));
        element.sendKeys(text);
    }

    public void selectByText(String text,
                             By locator) {
        logger.info(String.format("Selecting '%s' option in dropdown element '%s'", text, locator.toString()));

        getSelect(locator).selectByVisibleText(text);

    }

    public void selectByIndex(int index,
                              By locator) {
        getSelect(locator).selectByIndex(index);
    }

    @Attachment
    public byte[] screenshot() {
        logger.info(String.format("Taking a screenshot of current page (%s)", driver.getTitle()));

        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    protected WebElement waitForElementToBeVisible(final By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(200, TimeUnit.MILLISECONDS);

        return wait.until(ExpectedConditions.visibilityOf(find(locator)));
    }

    private Select getSelect(By locator) {
        return new Select(find(locator));
    }

    private WebDriver getDriver(WebElement element) {
        return ((WrapsDriver) element).getWrappedDriver();
    }
}
