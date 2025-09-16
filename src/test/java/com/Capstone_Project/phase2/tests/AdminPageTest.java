package com.Capstone_Project.phase2.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.Capstone_Project.phase2.pages.LoginPage;
import com.Capstone_Project.phase2.pages.AdminPage;
import com.Capstone_Project.utility.ScreenshotUtil;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.MediaEntityBuilder;

public class AdminPageTest {
    WebDriver driver;
    LoginPage loginPage;
    AdminPage adminPage;

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);

        ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/Phase2_AdminReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        test = extent.createTest("Phase2_AdminTest");
    }

    @Test
    public void dashboardMenuCountAndAdminNavigate() {
        try {
            loginPage.goToLoginPage();
            loginPage.login("Admin", "admin123");
            

            adminPage.openDashboardMenu();
            int menuCount = adminPage.getLeftMenuCount();
            System.out.println("Left menu options count: " + menuCount);

            adminPage.navigateToAdmin();
            test.pass("Dashboard menu counted (" + menuCount + ") and navigated to Admin");
        } catch (Exception e) {
            String path = ScreenshotUtil.getScreenshot(driver, "DashboardMenuFail");
            test.fail("Dashboard Menu/Admin navigation failed", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        }
    }

    @Test
    public void searchByUsername() {
        try {
            adminPage.searchByUsername("Admin");
            int count = adminPage.getTotalRecordsFound();
            System.out.println("Total record found: " + count);
            adminPage.refreshPage();
            test.pass("Search by Username Passed. Records found: " + count);
        } catch (Exception e) {
            String path = ScreenshotUtil.getScreenshot(driver, "SearchByUsernameFail");
            test.fail("Search By Username Failed", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        }
    }

    @Test
    public void searchByUserRole() {
        try {
            adminPage.searchByUserRole("Admin");
            int count = adminPage.getTotalRecordsFound();
            System.out.println("Total record found: " + count);
            adminPage.refreshPage();
            test.pass("Search By User Role Passed. Records found: " + count);
        } catch (Exception e) {
            String path = ScreenshotUtil.getScreenshot(driver, "SearchByUserRoleFail");
            test.fail("Search By User Role Failed", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        }
    }

    @Test
    public void searchByUserStatus() {
        try {
            adminPage.searchByUserStatus("Enabled");
            int count = adminPage.getTotalRecordsFound();
            System.out.println("Total record found: " + count);
            test.pass("Search By User Status Passed. Records found: " + count);
        } catch (Exception e) {
            String path = ScreenshotUtil.getScreenshot(driver, "SearchByUserStatusFail");
            test.fail("Search By User Status Failed", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        extent.flush();
    }
}
