package com.BrowserStack_project.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By usernameDropdown = By.cssSelector("#username");
    private By usernameInput = By.id("react-select-2-input");
    private By passwordDropdown = By.cssSelector("#password");
    private By passwordInput = By.id("react-select-3-input");
    private By loginButton = By.xpath("//button[normalize-space()='Log In']");
    private By errorMessage = By.cssSelector(".api-error");
    private By invalidUsernameHeading = By.xpath("//h3[text()='Invalid Username']");
    private By userProfile = By.cssSelector(".username");

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.elementToBeClickable(usernameDropdown)).click();
        driver.findElement(usernameInput).sendKeys(username + "\n");
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordDropdown)).click();
        driver.findElement(passwordInput).sendKeys(password + "\n");
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void login(String username, String password) {
        driver.get("https://bstackdemo.com/signin");
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        // wait for homepage user profile
        wait.until(ExpectedConditions.visibilityOfElementLocated(userProfile));
    }

    // Empty login with scroll to error message
    public void emptyLogin() {
        driver.get("https://bstackdemo.com/signin");
        clickLoginButton();
        // wait for error message
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        // scroll error message into view
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", driver.findElement(errorMessage));
        System.out.println("Error message is displayed!");
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLogoutDisplayed() {
        try {
            By logoutButton = By.xpath("//span[normalize-space()='Logout']");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getInvalidUsernameMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(invalidUsernameHeading)).getText();
        } catch (Exception e) {
            return null;
        }
    }
}
