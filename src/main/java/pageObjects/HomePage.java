package pageObjects;

import core.CoreUtils;
import exceptions.SeleniumFrameworkException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends CoreUtils {

    WebDriver driver;

    public HomePage(WebDriver driver)
    {
        super(driver);
        //initialization
        this.driver=driver;
        PageFactory.initElements(driver, this);

    }
    private By myaccount = By.xpath("//a[@title='My Account']");

    private By register = By.linkText("Register");


    private By login = By.linkText("Login");

    private By desktop = By.xpath("//a[contains(text(),'Desktops')]");
    private By showAll = By.xpath("//a[contains(text(),'Show All Desktops')]");


    public void goTo(String baseUrl) {

        driver.get(baseUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete")
        );

        // 2. Wait for jQuery (if present)
        try {
            wait.until(webDriver ->
                    (Boolean) ((JavascriptExecutor) webDriver)
                            .executeScript("return window.jQuery != null && jQuery.active === 0")
            );
        } catch (Exception ignored) {
            // jQuery not used on the site — safe to ignore
        }

        // 3. Wait for your element to be visible & clickable
        wait.until(ExpectedConditions.elementToBeClickable(desktop));

    }


    public RegisterUsers navigateToRegistrationPage(){
        try{
            Actions actions = new Actions(driver);
            WebElement userMenu = driver.findElement(By.xpath("//i[@class='fa fa-user']"));
            actions.moveToElement(userMenu).perform();
            click(myaccount);
            click(register);
            RegisterUsers registerUsers = new RegisterUsers(driver);
            return registerUsers;

        } catch(Exception e){
            throw new SeleniumFrameworkException("Failed to navigate to registration page ",e);
        }


    }

    public LoginUser navigateToLoginPage(){
        try{
            click( myaccount);
            click(login);
            LoginUser login = new LoginUser(driver);
            return login;
        }catch (Exception e){
            throw new SeleniumFrameworkException("Failed to navigate to Loginpage page ",e);
        }

    }

    public Desktops navigateToDesktopsPage(){
        moveToElement(desktop);
        click(showAll);
        Desktops desktop = new Desktops(driver);
        return desktop;

    }

}
