package com.mehmetg.weathertest.uitests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehmetg on 3/21/17.
 */
public class WeatherPage extends PageBase {

    /**
     * Element we want to interact with on the page.
     */
    // History navigation tab
    @FindBy(id = "city-nav-history")
    protected WebElement historyNavTab;

    @FindBy(className = "plot-needle")
    protected WebElement plotNeedle;

    @FindBy(className = "weather-graph")
    protected WebElement weatherGraph;

    @FindAll({@FindBy(className = "needle-label")})
    protected List<WebElement> needleLabels;

    @FindBy(id = "editMode")
    protected WebElement weatherGraphCustomizeButton;

    @FindBy(id = "plotVariablesMenu")
    protected WebElement weatherGraphCustomizationMenu;

    @FindBy(id = "cp_var_dewpoint")
    protected WebElement dewPointCheckBox;

    public WeatherPage(WebDriver driver, String url) throws MalformedURLException{
        super(driver, url);
    }
    public void navigateTo() {
        this.driver.get(this.targetURL.toString());
    }

    public void clickHistoryTab(){
        this.historyNavTab.click();
    }


    public void moveToWeatherGraph(int offsetX, int offsetY){

        Actions actionBuilder = new Actions(this.driver);
        actionBuilder.moveToElement(this.weatherGraph, offsetX, offsetY).build().perform();
    }

    public void moveToWeatherGraph(){
        this.waitForElementVisible(this.weatherGraph);
        Dimension wgd = this.weatherGraph.getSize();
        int offsetX = wgd.getWidth() / 2;
        int offsetY = wgd.getHeight() / 2;
        this.moveToWeatherGraph(offsetX, offsetY);
    }

    public boolean isPlotNeedleDisplayed(){
        return this.plotNeedle.isDisplayed();
    }

    public List<String> getVisibleNeedleLabels(){
        ArrayList<String> visibleNeedleLabels = new ArrayList<String>();
        for (WebElement label:this.needleLabels){
            if (label.isDisplayed()) {
                visibleNeedleLabels.add(label.getText());
            }
        }
        return visibleNeedleLabels;
    }

    public void openWeatherGraphCustomizationMenu(){
        this.waitForElementVisible(this.weatherGraph);
        this.weatherGraphCustomizeButton.click();
        this.waitForElementVisible(this.weatherGraphCustomizationMenu);
    }

    public boolean isDewPointCheckBoxChecked(){
        return this.dewPointCheckBox.isSelected();
    }

    public boolean isDewPointCheckBoxDisplayed(){
        return this.dewPointCheckBox.isDisplayed();
    }

    public void setDewPointCheckBoxChecked(boolean checked){
        if (this.dewPointCheckBox.isSelected() != checked) {
            // workaround for the lovely styled checkbox the real thing is not clickable
            // the id should be placed to the parent or both clickable components.
            // ideally the styled checkbox would need to have it's own class for easy use.
            this.dewPointCheckBox.findElement(By.xpath("..//label")).click();
        }
    }


}
