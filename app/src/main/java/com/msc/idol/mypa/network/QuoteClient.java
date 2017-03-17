package com.msc.idol.mypa.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.msc.idol.mypa.model.quote.Quote;

import org.json.JSONException;

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

public class QuoteClient extends Client {
    public static final String GET_QUOTE_URL = "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=1";

    public Quote getQuote() throws IOException, URISyntaxException {
        JsonElement element = getResponse(GET_QUOTE_URL);
        JsonArray quoteArray = element.getAsJsonArray();
        Quote quote = null;
        for (JsonElement e:quoteArray) {
            JsonObject quoteObject = e.getAsJsonObject();
            quote = new Quote(quoteObject.get("title").getAsString(), quoteObject.get("content").getAsString(), quoteObject.get("link").getAsString());
            break;
        }
        return quote;
    }
}
