package testhelpers.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testhelpers.base.BaseTest;
import testhelpers.reports.ExtentManager;
import testhelpers.utils.LoggerUtil;
import testhelpers.utils.ScreenshotUtils;

public class TestListeners implements ITestListener{
    private static final Logger log = LoggerUtil.getLogger(TestListeners.class);

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = ExtentManager.getInstance()
                .createTest(result.getMethod().getMethodName());
        ExtentManager.extentTest = test;
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.extentTest.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("TEST FAILED .."+result.getMethod().getMethodName());
        log.error("Reason: "+result.getThrowable().getMessage());
        ExtentManager.extentTest.log(Status.FAIL, result.getThrowable());
        // Fetch driver from your DriverManager
        WebDriver driver = (WebDriver)result.getAttribute("driver");


        if(driver != null) {
            // Capture screenshot
            String screenshotPath = ScreenshotUtils.captureScreenshot(
                    driver,
                    result.getMethod().getMethodName()
            );
            try {
                ExtentManager.extentTest.fail("Screenshot on failure", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

            } catch (Exception e) {
                ExtentManager.extentTest.fail("failed to attach screenshot" + e.getMessage());

            }
        }
        else{
                ExtentManager.extentTest.fail("Driver was null-screenshot not captureed");
            }

        }



    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.extentTest.log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }
}
