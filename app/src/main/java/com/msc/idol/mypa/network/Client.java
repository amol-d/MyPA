package com.msc.idol.mypa.network;

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
 * Created by adesai on 3/14/2017.
 */

public class Client {
    /**
     * Convenient method to fetch response from server
     *
     * @param rawUrl Server URL
     * @return JSONObject Converted server response string
     * @throws IOException
     * @throws URISyntaxException
     */
    protected JsonElement getResponse(String rawUrl) throws IOException, URISyntaxException {

        URL url = new URL(rawUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
//        connection.setDoOutput(true);

        // Get Response
        int status = connection.getResponseCode();
        StringBuilder response = new StringBuilder();

        if (status > 400) {
            InputStream is = connection.getErrorStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
        } else {
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
        }
        JsonParser parser = new JsonParser();
        JsonElement o = parser.parse(response.toString());


        return o;
    }
}
