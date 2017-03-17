package com.msc.idol.mypa.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.msc.idol.mypa.model.news.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by amdam_000 on 3/13/2017.
 */

public class NewsClient extends Client{
    public static final String GOOGLE_NEWS_URL = "https://newsapi.org/v1/articles?source=google-news&apiKey=ed7bb67e0ec84733a9d2713aa953f786";
    public static final String CNN_NEWS_URL = "https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=ed7bb67e0ec84733a9d2713aa953f786";
    public static final String TOI_NEWS_URL = "https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=top&apiKey=ed7bb67e0ec84733a9d2713aa953f786";
    public static final String CRIC_NEWS_URL = "https://newsapi.org/v1/articles?source=espn-cric-info&apiKey=ed7bb67e0ec84733a9d2713aa953f786";
    public static final String ESPN_SPORTS_NEWS_URL = "https://newsapi.org/v1/articles?source=espn&sortBy=top&apiKey=ed7bb67e0ec84733a9d2713aa953f786";

    public ArrayList<News> getCNNNews() throws IOException, URISyntaxException {
        String url = CNN_NEWS_URL;
        return getNews(getResponse(url));
    }

    public ArrayList<News> getTOINews() throws IOException, URISyntaxException {
        String url = TOI_NEWS_URL;
        return getNews(getResponse(url));
    }

    public ArrayList<News> getGoogleNews() throws IOException, URISyntaxException {
        String url = GOOGLE_NEWS_URL;
        return getNews(getResponse(url));
    }

    public ArrayList<News> getNews(JsonElement newsElement) {
        ArrayList<News> news = new ArrayList<>();
        try {
            JsonArray newsArray = newsElement.getAsJsonObject().getAsJsonArray("articles");
            for (JsonElement element : newsArray) {
                JsonObject newsObject = element.getAsJsonObject();
                news.add(new News(newsObject.get("title").getAsString(), newsObject.get("description").getAsString(), newsObject.get("url").getAsString(), newsObject.get("urlToImage").getAsString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }

    public ArrayList<News> getCricNews() throws IOException, URISyntaxException {
        return getNews(getResponse(CRIC_NEWS_URL));
    }

    public ArrayList<News> getSportNews() throws IOException, URISyntaxException {
        return getNews(getResponse(ESPN_SPORTS_NEWS_URL));
    }



}
