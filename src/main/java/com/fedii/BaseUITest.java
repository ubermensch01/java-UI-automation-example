package com.fedii;

import com.fedii.pages.amazon.AmazonLandingPage;
import com.fedii.tools.Config;
import com.fedii.tools.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Optional;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static ru.yandex.qatools.ashot.cropper.indent.IndentFilerFactory.blur;

/**
 * Created by sfedii on 3/22/16.
 */
public abstract class BaseUITest {
    private WebDriver driver;

    //    @Parameters({"browser"})
//    @BeforeMethod
    public void setUp(@Optional("local-ff") String browser) {
        driver = DriverFactory.getInstance().getDriver(browser);
    }

    //    @AfterMethod
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    /**
     * This method is protected to restrict changing the driver instance.
     *
     * @return instance of {@link WebDriver}
     */
    protected WebDriver getDriver() {
        return driver;
    }


    protected AmazonLandingPage getAmazonLandingPage() {
        getDriver().get(Config.getInstance().getAmazonUrl());

        return new AmazonLandingPage(getDriver());
    }

    @Attachment
    byte[] screenshot(By highlightedLocator) {
        return convert(new AShot()
                .imageCropper(new IndentCropper()
                        .addIndentFilter(blur()))
                .takeScreenshot(driver, driver.findElement(highlightedLocator)));
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
}
