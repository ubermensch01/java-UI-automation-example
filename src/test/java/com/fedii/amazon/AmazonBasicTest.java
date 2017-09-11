package com.fedii.amazon;

import com.fedii.BaseUITest;
import com.fedii.pages.amazon.AmazonLandingPage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Fedii
 * Date: 9/10/17
 * Time: 4:05 PM
 */
public class AmazonBasicTest extends BaseUITest {
    private AmazonLandingPage page;

    @BeforeClass
    public void setUp() {
        super.setUp("local-ff");
        page = getAmazonLandingPage();
    }

    @Test
    @Description("Verify user is not logged in by default")
    public void verifyUserIsLoggedOutByDefault() {
        Assert.assertTrue(!page.isLoggedIn(), "User is logged in by default!");
    }

    @Test
    @Description("Verify the initial query is empty")
    public void verifyInitialQueryIsEmpty() {
        Assert.assertEquals(page.getCurrentQuery(), "", "Query is not empty!");
    }

    @Test
    @Description("Verify the initial category is 'All Departments'")
    public void verifyInitialCategory() {
        Assert.assertEquals(page.getCurrentCategory(), "All Departments", "Initial category is not 'All Departments'!");
    }

    @Test
    @Description("Verify the initial cart count is 0")
    public void verifyInitialCartCount() {
        Assert.assertEquals(page.getCartItemCount(), 0, "Initial cart count is not zero!");
    }
    
    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
