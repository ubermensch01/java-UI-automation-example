package com.fedii.pages;

import com.fedii.tools.Config;
import io.qameta.allure.Attachment;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.yandex.qatools.ashot.cropper.indent.IndentFilerFactory.blur;

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

        return new WebDriverWait(driver, Config.POLLING_PERIOD).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> findList(By locator) {
        logger.info(String.format("Locating element '%s'", locator.toString()));

        return driver.findElements(locator);
    }

    public WebElement findIn(WebElement element,
                             By locator) {
        logger.info(String.format("Locating element '%s' in element %s", locator.toString(), element.toString()));

        return new WebDriverWait(driver, Config.POLLING_PERIOD)
                .withTimeout(Config.LOCATION_TIMEOUT, TimeUnit.MILLISECONDS)
                .pollingEvery(Config.POLLING_PERIOD, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
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

    @Attachment
    protected byte[] screenshot(By highlightedLocator) {
        logger.info(String.format("Taking a screenshot of current page (%s)", driver.getTitle()));

        return convert(new AShot()
                .imageCropper(new IndentCropper()
                        .addIndentFilter(blur()))
                .coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(driver, find(highlightedLocator)));
    }

    private byte[] convert(Screenshot screenshot) {
        return convert(screenshot.getImage());
    }

    private byte[] convert(BufferedImage image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageInByte = null;

        try {
            ImageIO.write(image, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageInByte;
    }

    protected WebElement waitForElementToBeVisible(final By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(200, TimeUnit.MILLISECONDS);

        return wait.until(ExpectedConditions.visibilityOf(find(locator)));
    }

    protected Select getSelect(By locator) {
        return new Select(find(locator));
    }

    private WebDriver getDriver(WebElement element) {
        return ((WrapsDriver) element).getWrappedDriver();
    }
}
