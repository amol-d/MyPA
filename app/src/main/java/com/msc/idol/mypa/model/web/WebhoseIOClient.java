package com.msc.idol.mypa.model.web;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;


public class WebhoseIOClient {

    private static final String WEBHOSE_BASE_URL = "http://webhose.io";
    private static WebhoseIOClient mClient;
    private String mNext;
    private String mApiKey;

    /**
     * Private constructor
     */
    private WebhoseIOClient() {
    }

    private WebhoseIOClient(String apiKey) {
        this.mApiKey = apiKey;
    }

    public static WebhoseIOClient getInstance(String apiKey) {
        if (mClient == null) {
            mClient = new WebhoseIOClient(apiKey);
        }

        return mClient;
    }

    /**
     * Convenient method to fetch response from server
     *
     * @param rawUrl Server URL
     * @return JSONObject Converted server response string
     * @throws IOException
     * @throws URISyntaxException
     */
    public JsonElement getResponse(String rawUrl) throws IOException, URISyntaxException {

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

        // Set next query URL
        mNext = WEBHOSE_BASE_URL + o.getAsJsonObject().get("next");

        return o;
    }

    public JsonElement query(String endpoint, String queries) throws URISyntaxException, IOException {
        try {
            /*URIBuilder builder = new URIBuilder(String.format("%s/%s?token=%s&format=json", WEBHOSE_BASE_URL, endpoint, mApiKey));
			for (String key : queries.keySet()) {
				builder.addParameter(key, queries.get(key));
			}*/
            String url = "https://webhose.io/search?token=" + mApiKey + "&format=json&q=" + queries;
            return getResponse(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get next response of current request
     *
     * @return JSONObject response
     */
    public JsonElement getNext() throws IOException, URISyntaxException {
        try {
            return getResponse(mNext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
