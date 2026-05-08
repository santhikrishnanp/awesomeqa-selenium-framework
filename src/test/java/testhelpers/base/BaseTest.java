package testhelpers.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import pageObjects.HomePage;
import testhelpers.config.ConfigManager;
import testhelpers.listeners.TestListeners;
import testhelpers.utils.LoggerUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;


@Listeners(TestListeners.class)
public class BaseTest extends ConfigManager {
    private static final Logger log = LoggerUtil.getLogger(BaseTest.class);
    public WebDriver driver;
    public HomePage homePage;
    public WebDriver initialiseWebDriver() throws IOException {

        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "/src/config-awsomeqa.properties");
        properties.load(fis);
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
        ChromeOptions options = new ChromeOptions();
        // ----------------------------
// 1. REMOTE (Docker Grid)
// ----------------------------
        if (browserName.contains("remote")) {

            if (browserName.contains("headless")) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,3000");
            }

            driver = new RemoteWebDriver(
                    new URL("http://localhost:4444/wd/hub"),
                    options
            );

        }
        // ----------------------------
        // 2. LOCAL (GitHub Chrome)
        // ----------------------------
        else if (browserName.contains("chrome")) {

            WebDriverManager.chromedriver().setup();

            if (browserName.contains("headless")) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,3000");
            }

            driver = new ChromeDriver(options);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }
    @BeforeMethod(alwaysRun=true)
    public HomePage launchHomePage(Method method, ITestResult result) throws IOException {
        log.info("===== STARTING TEST: " + method.getName() + " =====");
        String baseUrl = ConfigManager.getApplicationUrl();
        driver = initialiseWebDriver();
        result.setAttribute("driver",driver);
        homePage = new HomePage(driver);

        homePage.goTo(baseUrl);
        return homePage;

    }
    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        log.info("closing browser");
       driver.quit();
       log.info("****Test Finished****");

    }



}