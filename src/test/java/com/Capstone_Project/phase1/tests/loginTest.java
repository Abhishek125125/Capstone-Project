package com.Capstone_Project.phase1.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Capstone_Project.utility.ExcelUtil;
import com.Capstone_Project.utility.ScreenshotUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class loginTest {
    ExtentReports extent;
    ExtentTest test;

    WebDriver driver;
    String baseURL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    @BeforeTest
    public void startReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @BeforeClass
    public void setupExcel() throws Exception {
        ExcelUtil.setExcelFile("src/test/resources/data.xlsx", "HRM");
    }

    @BeforeMethod
    public void setupBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        driver.get(baseURL);
    }

    @DataProvider(name = "loginData")
    public Object[][] getData() throws Exception {
        return ExcelUtil.getTestData();
    }

    @Test(dataProvider = "loginData")
    public void LoginTest(String username, String password) throws Exception {
        test = extent.createTest("Login Test: " + username);

        Thread.sleep(2000); // Page load wait

        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Thread.sleep(3000); // Dashboard load wait

        boolean dashboardDisplayed = driver.findElements(By.xpath("//h6[text()='Dashboard']")).size() > 0;

        if(username.equals("Admin") && password.equals("admin123")) {
            Assert.assertTrue(dashboardDisplayed, "Valid login failed for: " + username);
            System.out.println("Test Passed for: " + username);

            // --- ADDED FOR REPORT ---
            test.pass("Successfully logged in for: " + username);
            // ------------------------

            // Screenshot after successful login
            ScreenshotUtil.getScreenshot(driver, username);

            // Logout
            driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
            driver.findElement(By.linkText("Logout")).click();
            System.out.println(" Successfully logged out for: " + username);
        } else {
            // Invalid login -> wait for error message before screenshot
            System.out.println(" Invalid login executed for: " + username);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']"))); // error message

            ScreenshotUtil.getScreenshot(driver, username);

            // --- ADDED FOR REPORT ---
            test.fail("Invalid login credentials: " + username + "/" + password);
            // ------------------------

            Assert.fail("Invalid login credentials: " + username + "/" + password);
        }
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }
}
