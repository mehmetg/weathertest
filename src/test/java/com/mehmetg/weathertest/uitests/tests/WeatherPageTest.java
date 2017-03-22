package com.mehmetg.weathertest.uitests.tests;


import com.mehmetg.weathertest.uitests.pages.HistoryPage;
import com.mehmetg.weathertest.uitests.pages.WeatherPage;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by mehmetg on 3/21/17.
 */
public class WeatherPageTest extends UITestBase {
    private static final String targetURL =
            "https://www.wunderground.com/us/ca/freedom/zmw:95073.1.99999";
    private static final String expectedTitleText = "Weather History";
    private static final String expectedURLText = "/history/";
    private static final String expectedHeaderText = "Weather Almanac";
    private static final String expectedHeaderTextAlt = "Weather History";
    private static final int expectedNeedleLabelCount = 6;

    protected WeatherPage weatherPage;

    @Factory(dataProvider = "browserConfigurations")
    public WeatherPageTest(DesiredCapabilities desiredCaps) {
        super(desiredCaps);
    }
    @Override
    @BeforeMethod
    public void setUp() throws Exception{
        super.setUp();
        this.weatherPage = new WeatherPage(this.driver, targetURL);
        weatherPage.navigateTo();
    }

    @Test
    public void historyTabLinkTest() throws MalformedURLException{
        this.weatherPage.clickHistoryTab();
        HistoryPage historyPage = new HistoryPage(this.driver);
        String title = historyPage.getTitle();
        Assert.assertTrue(
                title.contains(expectedTitleText),
                String.format("Title was expected to contain %1s, but was %2s", expectedTitleText, title)
        );
        String currentURL = historyPage.getCurrentURL();
        Assert.assertTrue(
                currentURL.contains(expectedURLText),
                String.format("URL was expected to contain %1s, but was %2s",
                        expectedURLText, currentURL)
        );
        String header = historyPage.getHistoryHeaderText();
        Assert.assertTrue(
                header.contains(expectedHeaderText) || header.contains(expectedHeaderTextAlt),
                String.format("URL was expected to contain %1s or %2s, but was %3s",
                        expectedHeaderText, expectedHeaderTextAlt, header)
        );

    }

    @Test
    public void plotNeedleTest() {
        this.weatherPage.moveToWeatherGraph();
        Assert.assertTrue(this.weatherPage.isPlotNeedleDisplayed(), "Plot needle not displayed!");
        List<String> visiblePlotNeedleLabels = this.weatherPage.getVisibleNeedleLabels();
        Assert.assertEquals(visiblePlotNeedleLabels.size(), expectedNeedleLabelCount,
                String.format("Expected %1d visible needle labels, but found %2d",
                        expectedNeedleLabelCount, visiblePlotNeedleLabels.size())
        );
        // Let's make sure label change when we move
        this.weatherPage.moveByOffset(20, 0);
        List<String> newVisiblePlotNeedleLabels = this.weatherPage.getVisibleNeedleLabels();
        Assert.assertEquals(newVisiblePlotNeedleLabels.size(), expectedNeedleLabelCount,
                String.format("Expected %1d visible needle labels after second move, but found %2d",
                        expectedNeedleLabelCount, newVisiblePlotNeedleLabels.size())
        );
        // Let's check if they all are the same or not.
        // Order is expected to be the same.
        boolean allEqual = true;
        for (int idx = 0; idx < visiblePlotNeedleLabels.size(); idx++) {
            if (!visiblePlotNeedleLabels.get(idx).contentEquals(newVisiblePlotNeedleLabels.get(idx))){
                allEqual = false;
                break;
            }
        }
        Assert.assertFalse(allEqual, "Plot needle labels have not changed after second move!");
    }

    @Test
    public void customizeDropDownTest() {
        this.weatherPage.openWeatherGraphCustomizationMenu();
        Assert.assertTrue(this.weatherPage.isDewPointCheckBoxDisplayed(), "Dew Point checkbox is not visible!");
        Assert.assertFalse(this.weatherPage.isDewPointCheckBoxChecked(), "Dew Point checkbox is checked!");
        this.weatherPage.setDewPointCheckBoxChecked(true);
        Assert.assertTrue(this.weatherPage.isDewPointCheckBoxChecked(), "Dew Point checkbox is not checked!");
    }
}
