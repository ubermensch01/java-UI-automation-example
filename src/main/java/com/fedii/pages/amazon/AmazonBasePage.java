package com.fedii.pages.amazon;

import com.fedii.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Fedii
 * Date: 9/10/17
 * Time: 7:57 PM
 */
public class AmazonBasePage extends BasePage {

    private static final By signInLinkLocator = By.cssSelector("a#nav-link-accountList");
    private static final By searchInputLocator = By.cssSelector("input#twotabsearchtextbox");
    private static final By searchDropdownLocator = By.cssSelector("select#searchDropdownBox");
    private static final By cartCountLocator = By.cssSelector("span#nav-cart-count");

    public AmazonBasePage(WebDriver driver) {
        super(driver);
    }


    @Step
    public AmazonSearchResultsPage search(String query) {
        type(query + String.valueOf(Keys.ENTER), searchInputLocator);
        screenshot(searchInputLocator);

        return new AmazonSearchResultsPage(driver);
    }

    public boolean isLoggedIn() {
        try {
            driver.findElement(signInLinkLocator);
            screenshot(signInLinkLocator);
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public String getCurrentQuery() {
        screenshot(searchInputLocator);
        return find(searchInputLocator).getText();
    }

    public String getCurrentCategory() {
        screenshot(searchDropdownLocator);
        return getSelect(searchDropdownLocator)
                .getFirstSelectedOption()
                .getText();
    }

    public int getCartItemCount() {
        screenshot(cartCountLocator);
        return Integer.parseInt(find(cartCountLocator)
                .getText());
    }
}
