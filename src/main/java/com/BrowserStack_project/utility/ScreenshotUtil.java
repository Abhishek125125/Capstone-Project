package com.BrowserStack_project.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtil {
    public static String getScreenshot(WebDriver driver, String filename) {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File temp = ts.getScreenshotAs(OutputType.FILE);
        String folderPath = System.getProperty("user.dir") + File.separator + "Screenshot";
        File dir = new File(folderPath);
        if(!dir.exists()) dir.mkdirs();
        File dest = new File(folderPath + File.separator + filename + "_" + timestamp + ".png");
        try
        {
        	FileHandler.copy(temp, dest); 
        	}
        catch(IOException e)
        {
        	e.printStackTrace(); 
        	}
        return dest.getAbsolutePath();
    }
}
