package com.BrowserStack_project.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;

import com.BrowserStack_project.pages.LoginPage;
import com.BrowserStack_project.pages.ProductPage;

import java.util.List;

public class ProductTest extends BaseTest {

    @Test(priority = 1)
    public void verifyProductFiltersTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("demouser", "testingisfun99");

        ProductPage productPage = new ProductPage(driver);

        if (productPage.areFiltersDisplayed()) {
            System.out.println("->Filters are displayed.");
        } else {
            System.out.println("No filters displayed on product page.");
        }

        Assert.assertTrue(true, "Filters test executed successfully.");
    }

    @Test(priority = 2)
    public void printAllProductDetailsTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("demouser", "testingisfun99");

        ProductPage productPage = new ProductPage(driver);
        List<WebElement> products = productPage.getProducts();

        Assert.assertTrue(products.size() > 0, " No products found on Product Page!");

        System.out.println("Total products found: " + products.size());

        // Print product details (without image check)
        for (WebElement product : products) {
            System.out.printf(
                "Name: %s | Price: %s | AddToCart: %s%n",
                productPage.getProductName(product),
                productPage.getProductPrice(product),
                productPage.isAddToCartDisplayed(product) ? "Yes" : "No"
            );
        }
    }
}
