package com.BrowserStack_project.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.BrowserStack_project.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("demouser", "testingisfun99");
        Assert.assertTrue(loginPage.isLogoutDisplayed(), "Homepage not loaded after valid login!");
    }

    @Test
    public void invalidLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wronguser", "wrongpass");
        // ❌ Logout should not be visible
        Assert.assertFalse(loginPage.isLogoutDisplayed(), "Invalid login should not go to homepage!");
    }

    @Test
    public void emptyLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.emptyLogin();

        // ❌ Fail the test deliberately if Logout is displayed
        Assert.assertTrue(loginPage.isLogoutDisplayed(), "Empty login should not go to homepage!");
    }
}
