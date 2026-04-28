package tests;

import com.sun.net.httpserver.Authenticator;
import datautils.JsonReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.*;
import testhelpers.base.BaseTest;
import testhelpers.listeners.RetryListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class addToCartAndCheckout extends BaseTest {

    @Test(groups = {"Regression"},retryAnalyzer = RetryListener.class)
    public void testBuyIPhone() throws InterruptedException {
        Desktops allDesktops = homePage.navigateToDesktopsPage();

        allDesktops.addToCarts();
        Checkout checkout = new Checkout(driver);
        System.out.println(checkout.confirmCheckoutSize());
    }


}
