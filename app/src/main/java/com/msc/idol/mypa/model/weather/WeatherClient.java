package com.msc.idol.mypa.model.weather;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by amdam_000 on 3/13/2017.
 */

public class WeatherClient {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?appid=%s";

    public JsonElement getWeatherForLatLon(String lat, String lon) throws IOException, URISyntaxException {
        String url = String.format(BASE_URL + "&lat=%s&lon=%s", WeatherUtils.API_KEY, lat, lon);
        return getResponse(url);
    }

    public JsonElement getWeatherForCity(String city) throws IOException, URISyntaxException {
        String url = String.format(BASE_URL + "&q=%s", WeatherUtils.API_KEY, city);
        return getResponse(url);
    }

    /**
     * Convenient method to fetch response from server
     *
     * @param rawUrl Server URL
     * @return JSONObject Converted server response string
     * @throws IOException
     * @throws URISyntaxException
     */
    private JsonElement getResponse(String rawUrl) throws IOException, URISyntaxException {

        URL url = new URL(rawUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        // Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();

        JsonParser parser = new JsonParser();
        JsonElement o = parser.parse(response.toString());


        return o;
    }
}
