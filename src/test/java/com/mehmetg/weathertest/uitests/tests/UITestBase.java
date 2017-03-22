package com.mehmetg.weathertest.uitests.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.URL;

/**
 * Created by mehmetg on 3/21/17.
 */
public class UITestBase {

    protected WebDriver driver;
    protected DesiredCapabilities desiredCapabilities;

//    @Factory(dataProvider = "browserConfigurations")
//    public UITestBase(DesiredCapabilities desiredCaps) {
//        this.desiredCapabilities = desiredCaps;
//    }

    @DataProvider(name = "browserConfigurations", parallel = true)
    public static Object[][] browserConfigurations() {
        return new Object[][]{
                new Object[]{DesiredCapabilities.chrome()},
                new Object[]{DesiredCapabilities.firefox()},
                // new Object[]{DesiredCapabilities.safari()}
        };
    }

    @BeforeMethod
    public void setUp() throws Exception {
        this.driver = new RemoteWebDriver(
                new URL("http", "localhost", 4444, "/wd/hub"),
                DesiredCapabilities.chrome()
                //this.desiredCapabilities
        );
    }

    @AfterMethod
    public void tearDown() throws Exception {
        try {
            this.driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
