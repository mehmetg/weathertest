package com.mehmetg.weathertest.apitests;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehmetg.weathertest.apitests.models.WeatherApiResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 * Created by mehmetg on 3/22/17.
 */
public class WeatherApiTest {
    private static final String targetURL =
            "http://api.wunderground.com/api/5c25f5ea989a5950/conditions/q/CA/San_Francisco.json";
    // instructions ask for a value of "v0.1" and that would make the test fail. ??
    private static final String expectedVersion = "0.1";
    private static final String dateTimePrefix = "Last Updated on ";
    // This is the same as the update period for data. Worst case we can be 5 mins apart.
    private static final long maxTimeDeltaMillis =  TimeUnit.MINUTES.toMillis(5);


    private WeatherApiResponse response;
    private Calendar currentDateTime;
    @BeforeTest
    public void setUp(){
        this.response = this.getApiResponse(targetURL);
        this.currentDateTime = Calendar.getInstance();
    }

    private static WeatherApiResponse getApiResponse(String url){
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            int status_code = response.getStatusLine().getStatusCode();
            if (status_code < 200 && status_code >= 300) {
                System.err.println(String.format("Request failed with code %1d", status_code));
                return null;
            }
            ObjectMapper responseMapper = new ObjectMapper();
            JsonParser responseParser = responseMapper.getFactory().createParser(response.getEntity().getContent());
            return responseParser.readValueAs(WeatherApiResponse.class);
        } catch (IOException ioe) {
            // Malformed response.
            ioe.printStackTrace();
            return null;
        }

    }

    /**
     * Converts celcius temp to fahrenheit and assumes single digit of precision.
     * @param celsius
     * @return
     */
    private static double toFahrenheit(double celsius){
        return Math.round((9 * (celsius / 5) + 32) * 10.0)/ 10.0;

    }

    @Test
    public void versionTest(){
        Assert.assertEquals(this.response.getResponse().getVersion(), expectedVersion,
                String.format("Expected %1s received %2s", expectedVersion, this.response.getResponse().getVersion())
        );
    }

    @Test
    public void celsiusToFahrenheitConversionTest(){
        double fahrenheitExpected = toFahrenheit(this.response.getCurrentObservation().getTempC());
        Assert.assertEquals(this.response.getCurrentObservation().getTempF(), fahrenheitExpected,
                String.format("Expected %1f received %2f",
                        fahrenheitExpected, this.response.getCurrentObservation().getTempF() )
        );
    }

    @Test
    public void observationTimeDateTest(){
        //There's still a risk of false failure at around new year's eve, but it is minimal.
        String dateTimeString = this.response.getCurrentObservation().getObservationTime();
        dateTimeString = dateTimeString.replace(dateTimePrefix, "");
        long epochUpdateTimeFromResponse = convertStringDateToZonedDateTime(dateTimeString);
        long epochTimeOfTestRequest = this.currentDateTime.toInstant().toEpochMilli();
        long delta = Math.abs(epochTimeOfTestRequest - epochUpdateTimeFromResponse);
        Assert.assertTrue(delta <= maxTimeDeltaMillis,
                String.format("Time between test time and resonse time was %1d, which is larger than %2d",
                        delta, maxTimeDeltaMillis)
        );


    }

    private long convertStringDateToZonedDateTime(String dts) {

        try {
            DateFormat df = new SimpleDateFormat("MMMM d, h:m a z y");
            // parse after adding year for later comparison
            Date d = df.parse(String.format("%1s %2d", dts, this.currentDateTime.get(Calendar.YEAR)));
            return d.toInstant().toEpochMilli();
        } catch (ParseException pe) {
            pe.printStackTrace();
            return 0;
        }
    }
}
