package com.msc.idol.mypa.model.news;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

public class NewsClient {
    public static final String GOOGLE_NEWS_URL = "https://newsapi.org/v1/articles?source=google-news&apiKey=ed7bb67e0ec84733a9d2713aa953f786";
    public static final String CRIC_NEWS = "https://newsapi.org/v1/articles?source=espn-cric-info&apiKey=ed7bb67e0ec84733a9d2713aa953f786";
    public JsonElement getGoogleNews() throws IOException, URISyntaxException {
        String url = GOOGLE_NEWS_URL;
        return getResponse(url);
    }

    public JsonElement getCricNews() throws IOException, URISyntaxException {
        return getResponse(CRIC_NEWS);
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
