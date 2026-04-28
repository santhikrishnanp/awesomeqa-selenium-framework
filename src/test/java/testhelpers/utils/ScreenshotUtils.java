package testhelpers.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import testhelpers.listeners.TestListeners;

import java.io.File;
import java.nio.file.Files;

public class ScreenshotUtils {
    private static final Logger log = LoggerUtil.getLogger(TestListeners.class);
    public static String captureScreenshot(WebDriver driver, String testName) {
        log.info("Capturing screenshot for failed test...");

        String timestamp = String.valueOf(System.currentTimeMillis());
        String screenshotPath = "reports/screenshots/" + testName + "_" + timestamp + ".png";

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File dest = new File(screenshotPath);
            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Screenshot saved at"+ screenshotPath);
        return screenshotPath;
    }
}
