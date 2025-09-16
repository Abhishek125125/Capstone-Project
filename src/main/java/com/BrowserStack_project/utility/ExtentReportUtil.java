package com.BrowserStack_project.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReportUtil {

    public static ExtentReports extent;
    public static ExtentTest test;

    // Initialize ExtentReports (call this in @BeforeSuite)
    public static void initReports() {
        if (extent == null) { // avoid re-initialization
            try {
                // Ensure reports folder exists
                File reportDir = new File("reports");
                if (!reportDir.exists()) {
                    reportDir.mkdir();
                }

                ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/report.html");
                extent = new ExtentReports();
                extent.attachReporter(sparkReporter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Create a test in the report
    public static void startTest(String testName) {
        if (extent == null) {
            initReports(); // safe fallback
        }
        test = extent.createTest(testName);
    }

    // Flush reports (call this in @AfterSuite)
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
