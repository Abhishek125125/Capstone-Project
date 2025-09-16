package com.BrowserStack_project.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.BrowserStack_project.pages.CartPage;
import com.BrowserStack_project.pages.LoginPage;

public class AddToCartTest extends BaseTest {

    @Test(priority = 4)
    public void addcart() {
        // Login once
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("demouser", "testingisfun99");

        CartPage cartPage = new CartPage(driver);

        // --- Step 1: Add single item ---
        System.out.println("===== Step 1: Adding single item =====");
        cartPage.addItemById("1");
        cartPage.openCart();
        int countSingle = cartPage.getCartItemCount();
        System.out.println("Cart Count after adding 1 item: " + countSingle);
        Assert.assertEquals(countSingle, 1, " Cart count is not 1 after adding single item!");

        // --- Step 2: Add multiple items (IDs 2,4,5) ---
        System.out.println("\n===== Step 2: Adding multiple items =====");
        cartPage.addItemById("2");
        cartPage.addItemById("4");
        cartPage.addItemById("5");
        cartPage.openCart();
        int countMultiple = cartPage.getCartItemCount();
        System.out.println("Cart Count after adding multiple items: " + countMultiple);
        Assert.assertEquals(countMultiple, 4, " Cart count is not 4 after adding multiple items!");

        // --- Step 3: Remove items 4 & 5 ---
        System.out.println("\n===== Step 3: Removing items =====");
        int beforeRemove = cartPage.getCartItemCount();
        System.out.println("Cart Count before removal: " + beforeRemove);
        cartPage.removeItemByIndex(2); // Remove 4th item
        cartPage.removeItemByIndex(2); // Remove 5th item (index shifted)
        int afterRemove = cartPage.getCartItemCount();
        System.out.println("Cart Count after removal: " + afterRemove);
        Assert.assertEquals(afterRemove, 2, " Items were not removed properly!");
    }
}
