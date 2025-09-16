package com.BrowserStack_project.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.BrowserStack_project.pages.CartPage;
import com.BrowserStack_project.pages.CheckoutPage;
import com.BrowserStack_project.pages.LoginPage;

public class CheckoutTest extends BaseTest {

    // TC_007: Place order with valid details
    @Test(priority = 7)
    public void checkoutWithItems() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("demouser", "testingisfun99");

        CartPage cartPage = new CartPage(driver);
        cartPage.addItemById("2");
        cartPage.addItemById("4");
        cartPage.addItemById("5");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.goToCheckout();           
        checkoutPage.fillShippingDetails();
        checkoutPage.submitCheckout();

        boolean checkoutReached = driver.getCurrentUrl().contains("checkout");
        Assert.assertTrue(checkoutReached, " Checkout page not reached after adding items!");
    }

    // TC_008: Checkout flow without adding items (negative test)
 // TC_008: Checkout flow without adding items (negative test)
    @Test(priority = 8)
    public void checkoutWithoutItems() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("demouser", "testingisfun99");

        CartPage cartPage = new CartPage(driver);
        cartPage.openCart(); // Open empty cart

        boolean checkoutVisible = false;
        try {
            WebElement checkoutBtn = driver.findElement(
                    By.xpath("//div[@class='float-cart__footer']//div[@class='buy-btn']")
            );
            checkoutVisible = checkoutBtn.isDisplayed() && checkoutBtn.isEnabled();
        } catch (Exception e) {
            // Element not present or not visible → expected for empty cart
            checkoutVisible = false;
        }

        if (!checkoutVisible) {
            System.out.println(" Checkout option is not there as expected");
        }
        Assert.assertFalse(checkoutVisible, "Checkout button should not be visible when cart is empty!");
    }

}
