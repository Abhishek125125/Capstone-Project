package com.BrowserStack_project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Go to checkout from cart
    public void goToCheckout() {
        try {
            WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='float-cart__footer']//div[@class='buy-btn']")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutBtn);
            checkoutBtn.click();
        } catch (Exception e) {
            System.out.println(" Unable to go to checkout → " + e.getMessage());
        }
    }

    // Fill shipping form
    public void fillShippingDetails() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstNameInput"))).sendKeys("John");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastNameInput"))).sendKeys("Doe");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addressLine1Input"))).sendKeys("123 Main St");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("provinceInput"))).sendKeys("California");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("postCodeInput"))).sendKeys("12345");
        } catch (Exception e) {
            System.out.println(" Unable to fill shipping details → " + e.getMessage());
        }
    }

    // Submit checkout
    public void submitCheckout() {
        try {
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("checkout-shipping-continue")
            ));
            continueBtn.click();
            System.out.println(" Order successfully submitted");
        } catch (Exception e) {
            System.out.println(" Unable to submit checkout → " + e.getMessage());
        }
    }
}
