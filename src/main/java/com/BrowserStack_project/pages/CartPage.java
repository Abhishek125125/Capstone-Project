package com.BrowserStack_project.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Add item by ID
    public void addItemById(String id) {
        try {
            if (id.equals("3")) {
                System.out.println("⚠ Skipping ID 3 as it doesn't exist.");
                return;
            }

            WebElement addBtn = driver.findElement(
                By.xpath("//div[@id='" + id + "']//div[contains(text(),'Add to cart')]")
            );

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);

            // Wait until clickable
            wait.until(ExpectedConditions.elementToBeClickable(addBtn));

            // Try normal click, fallback to JS click if intercepted
            try {
                addBtn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
            }

            System.out.println("Added item with ID: " + id);

        } catch (Exception e) {
            System.out.println(" Unable to add item with ID: " + id + " → " + e.getMessage());
        }
    }

    // Open cart
 // Open cart
    public void openCart() {
        try {
            // Check if cart is already open
            if (!driver.findElements(By.cssSelector(".float-cart__shelf-container")).isEmpty()) {
                System.out.println(" Cart is already open, skipping open action.");
                return;
            }

            WebElement cartIcon = driver.findElement(By.cssSelector("span.cart-count"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cartIcon);
            wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".float-cart__shelf-container")
            ));
        } catch (Exception e) {
            System.out.println(" Unable to open cart → " + e.getMessage());
        }
    }


    // Close cart
    public void closeCart() {
        try {
            WebElement closeBtn = driver.findElement(By.cssSelector("span.float-cart__close-btn"));
            wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
        } catch (Exception e) {
            System.out.println(" Unable to close cart → " + e.getMessage());
        }
    }

    // Get current cart count
    public int getCartItemCount() {
        try {
            List<WebElement> items = driver.findElements(
                By.cssSelector(".float-cart__shelf-container .shelf-item")
            );
            return items.size();
        } catch (Exception e) {
            System.out.println(" Unable to get cart count → " + e.getMessage());
            return 0;
        }
    }

    // Remove item by index
    public void removeItemByIndex(int index) {
        try {
            List<WebElement> items = driver.findElements(
                By.cssSelector(".float-cart__shelf-container .shelf-item")
            );

            if (index < items.size()) {
                WebElement removeBtn = items.get(index).findElement(By.cssSelector(".shelf-item__del"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", removeBtn);
                wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
                wait.until(ExpectedConditions.stalenessOf(items.get(index)));
                System.out.println("-- Removed item at index: " + index);
            }

        } catch (Exception e) {
            System.out.println(" Unable to remove item at index: " + index + " → " + e.getMessage());
        }
    }
}
