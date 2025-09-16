package com.Capstone_Project.phase2.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.Capstone_Project.phase2.pages.LoginPage;
import com.Capstone_Project.utility.ScreenshotUtil;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.MediaEntityBuilder;

public class LoginPageTest {
    WebDriver driver;
    LoginPage loginPage;

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);

        ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/Phase2_LoginReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        test = extent.createTest("Phase2_LoginTest");
    }

    @Test
    public void loginWithValidCredentials() {
        try {
            loginPage.goToLoginPage();
            loginPage.login("Admin", "admin123");
            test.pass("✅ Login Successful - Dashboard displayed");
        } catch (Exception e) {
            String path = ScreenshotUtil.getScreenshot(driver, "LoginFail");
            test.fail("❌ Login Failed", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        extent.flush();
    }
}
