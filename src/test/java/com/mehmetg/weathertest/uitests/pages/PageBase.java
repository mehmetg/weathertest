package com.mehmetg.weathertest.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mehmetg on 3/21/17.
 */
public class PageBase {

    /**
     * Creates the page object using the webdriver object passed
     * @param driver WebDriver instance
     */
    public PageBase(WebDriver driver, String url) throws MalformedURLException{
        this.targetURL = new URL(url);
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public PageBase(WebDriver driver) throws MalformedURLException{
        this.driver = driver;
        this.targetURL = new URL(this.driver.getCurrentUrl());
        PageFactory.initElements(this.driver, this);
    }

    /**
     * Some members we need to use the driver and other useful page object stuff.
     * Members are protected only as we may want to extend this class at some point.
     */
    protected WebDriver driver;

    protected URL targetURL;

    public URL getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) throws MalformedURLException {
        this.targetURL = new URL(targetURL);
    }

    public String getTitle(){
        return this.driver.getTitle();
    }

    public String getCurrentURL(){
        return this.driver.getCurrentUrl();
    }

    public void moveByOffset(int offsetX, int offsetY){
        Actions actionBuilder = new Actions(this.driver);
        actionBuilder.moveByOffset(offsetX, offsetY).build().perform();
    }

    public void waitForElementVisible(WebElement element){
        new WebDriverWait(this.driver, 60, 100)
                .until(ExpectedConditions.visibilityOf(element));
    }
}
