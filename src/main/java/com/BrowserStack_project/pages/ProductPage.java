package com.BrowserStack_project.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductPage {

    private WebDriver driver;

    @FindBy(css = ".shelf-item")
    private List<WebElement> products;

    @FindBy(css = ".filters-available-size")
    private WebElement filters;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ✅ Get all products
    public List<WebElement> getProducts() {
        // Scroll once to load lazy content
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        return products;
    }

    // ✅ Verify filters
    public boolean areFiltersDisplayed() {
        try {
            return filters.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Product Name
    public String getProductName(WebElement product) {
        return product.findElement(By.cssSelector(".shelf-item__title")).getText();
    }

    //  Product Price
    public String getProductPrice(WebElement product) {
        return product.findElement(By.cssSelector(".val")).getText();
    }

    //Add to Cart Button
    public boolean isAddToCartDisplayed(WebElement product) {
        try {
            return product.findElement(By.cssSelector(".shelf-item__buy-btn")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
