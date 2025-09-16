package com.Capstone_Project.phase2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By dashboardMenu = By.cssSelector("button.oxd-icon-button.oxd-main-menu-button");
    By leftMenuItems = By.cssSelector("ul.oxd-main-menu > li");
    By adminMenuItem = By.xpath("//span[text()='Admin']/parent::a");

    By usernameInput = By.xpath("//label[text()='Username']/following::input[1]");
    By userRoleDropdown = By.xpath("//label[text()='User Role']/following::div[contains(@class,'oxd-select-text')]");
    By statusDropdown = By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-text')]");
    By searchButton = By.xpath("//button[@type='submit']");
    By resultRows = By.cssSelector("div.oxd-table-card");

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openDashboardMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(dashboardMenu)).click();
    }

    public int getLeftMenuCount() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(leftMenuItems));
        return driver.findElements(leftMenuItems).size();
    }

    public void navigateToAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(adminMenuItem)).click();
    }

    public void searchByUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).clear();
        driver.findElement(usernameInput).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    // ---------- SEARCH BY USER ROLE WITH BLUR FIX ----------
    public void searchByUserRole(String role) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown));
        dropdown.click();

        // Select role
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//label[text()='User Role']/following::div[contains(@class,'oxd-select-dropdown')]//span")
        ));

        boolean found = false;
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(role)) {
                option.click();
                found = true;
                break;
            }
        }
        if (!found) throw new RuntimeException("User Role option not found: " + role);

        // Trigger blur to register selection
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].blur();", dropdown);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    // ---------- SEARCH BY USER STATUS WITH BLUR FIX ----------
    public void searchByUserStatus(String status) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(statusDropdown));
        dropdown.click();

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-dropdown')]//span")
        ));

        boolean found = false;
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(status)) {
                option.click();
                found = true;
                break;
            }
        }
        if (!found) throw new RuntimeException("Status option not found: " + status);

        // Trigger blur
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].blur();", dropdown);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public int getTotalRecordsFound() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(resultRows));
        return driver.findElements(resultRows).size();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }
}
