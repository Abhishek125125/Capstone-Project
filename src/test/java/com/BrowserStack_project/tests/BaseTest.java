package com.BrowserStack_project.tests;

import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.BrowserStack_project.utility.ExtentReportUtil;
import com.BrowserStack_project.utility.ScreenshotUtil;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp(Method method) {
        // Initialize driver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Open base URL
        driver.get("https://bstackdemo.com/signin?offers=true");

        // Initialize ExtentTest for current test
        ExtentReportUtil.startTest(method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            // ✅ Wait for UI/process to complete before screenshot
            if (result.getStatus() == ITestResult.SUCCESS || result.getStatus() == ITestResult.FAILURE) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                // Wait until either Logout button or error message is visible
                try {
                    wait.until(d -> d.findElements(By.xpath("//span[normalize-space()='Logout']")).size() > 0
                            || d.findElements(By.cssSelector(".api-error")).size() > 0);
                } catch (Exception ignored) {
                }

                String screenshotPath = ScreenshotUtil.getScreenshot(driver, result.getName());

                if (result.getStatus() == ITestResult.FAILURE) {
                    ExtentReportUtil.test.fail(" -Test Failed",
                            ExtentReportUtil.test.addScreenCaptureFromPath(screenshotPath)
                                    .getModel().getMedia().get(0));
                } else {
                    ExtentReportUtil.test.pass(" -Test Passed",
                            ExtentReportUtil.test.addScreenCaptureFromPath(screenshotPath)
                                    .getModel().getMedia().get(0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Quit driver
            if (driver != null) {
                driver.quit();
            }
            // Flush reports after every test method
            ExtentReportUtil.flushReports();
        }
    }
}
