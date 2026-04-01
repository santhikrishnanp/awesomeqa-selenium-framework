package pageObjects;

import core.CoreUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterUsers extends CoreUtils {
    WebDriver driver;

    public RegisterUsers(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    private By username = By.cssSelector("input[name='firstname']");
//    @FindBy(css = "input[name='firstname']")

    private By lastname = By.id("input-lastname");
    private By mail = By.id("input-email");
    private By telephone = By.name("telephone");
    private By password = By.name("password");
    private By confirm = By.name("confirm");
    private By subscribe = By.xpath("//label[normalize-space()='No']");
    private By terms = By.cssSelector("input[type='checkbox']");
    private By submit = By.xpath("//input[@value='Continue']");

    public LoginUser enterRegistrationDetails(String firstname,String lname,String email,String phone,String pass) throws InterruptedException {
       sendKeys( username,firstname);
        sendKeys( lastname,lname);
        sendKeys( mail,email);
        sendKeys( telephone,phone);
        sendKeys( password,pass);
        sendKeys( confirm,pass);
        click( subscribe);
        WebElement element = driver.findElement(terms);
        if(!element.isSelected()){
            click( terms);
        }
        click( submit);
//        submit.click();
        Thread.sleep(3000);

       LoginUser login = new LoginUser(driver);
        return login;

    }

}
