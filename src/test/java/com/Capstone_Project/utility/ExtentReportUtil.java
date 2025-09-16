package com.Capstone_Project.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtil {
    private static ExtentReports extent;   // Report object
    private static ExtentTest test;        // Current test

    // Start report
    public static void startReport(String fileName) {
        ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/" + fileName);
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    // Create test
    public static void startTest(String testName) {
        test = extent.createTest(testName);
    }

    // Get current test
    public static ExtentTest getTest() {
        return test;
    }

    // Log Pass
    public static void logPass(String message) {
        if (test != null) {
            test.log(Status.PASS, message);
        }
    }

    // Log Fail
    public static void logFail(String message, Throwable t) {
        if (test != null) {
            test.log(Status.FAIL, message);
            test.log(Status.FAIL, t);
        }
    }

    // End report
    public static void endReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
