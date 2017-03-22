package com.mehmetg.weathertest.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.MalformedURLException;

/**
 * Created by mehmetg on 3/21/17.
 */
public class HistoryPage extends PageBase{

    @FindBy(className = "history-header")
    protected WebElement historyHeader;

    public HistoryPage(WebDriver driver) throws MalformedURLException{
        super(driver);
    }

    public String getHistoryHeaderText(){
        return this.historyHeader.getText();
    }
}
