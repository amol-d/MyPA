package com.msc.idol.mypa.network;

import android.os.StrictMode;
import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.msc.idol.mypa.model.web.WebResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Request;
import okhttp3.Response;


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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        int respCode = connection.getResponseCode();
        InputStream is;
        if (respCode > 400) {
            is = connection.getErrorStream();
        } else {
            is = connection.getInputStream();
        }
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

    public ArrayList<WebResult> query(String endpoint, String queries) throws URISyntaxException, IOException {

        ArrayList<WebResult> webResults = new ArrayList<>();
        try {
            /*URIBuilder builder = new URIBuilder(String.format("%s/%s?token=%s&format=json", WEBHOSE_BASE_URL, endpoint, mApiKey));
            for (String key : queries.keySet()) {
				builder.addParameter(key, queries.get(key));
			}*/
            String url = "https://webhose.io/search?token=" + mApiKey + "&format=json&q=" + queries /*+ " language:english"*/;
            JsonElement result = getResponse(url);


            JsonArray postArray = result.getAsJsonObject().getAsJsonArray("posts");

            for (JsonElement o : postArray) {
                JsonObject webResult = o.getAsJsonObject();
                if (webResult != null) {
                    if (!TextUtils.isEmpty(webResult.get("language").getAsString()) && (webResult.get("language").getAsString().toLowerCase().equalsIgnoreCase("english") || webResult.get("language").getAsString().toLowerCase().equalsIgnoreCase("hindi")))
                        webResults.add(new WebResult(webResult.get("title").getAsString(), webResult.get("author").getAsString(), webResult.get("text").getAsString(), webResult.get("url").getAsString(), webResult.get("language").getAsString()));
                }
            }
            return webResults;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return webResults;
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
