package com.msc.idol.mypa.chat.actions;

import com.msc.idol.mypa.model.news.News;
import com.msc.idol.mypa.network.NewsClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by adesai on 3/15/2017.
 */

public class NewsAction implements Action {
    String input;

    @Override
    public ArrayList<String> getRecognizer() {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("news");
        queries.add("feeds");
        queries.add("breaking");
        queries.add("Sports");
        queries.add("cricket");
        queries.add("cnn");
        return queries;
    }

    @Override
    public boolean isActionFitting(String input) {
        for (String q : getRecognizer()) {
            if (q.toLowerCase().contains(input.toLowerCase())) {
                this.input = input.toLowerCase();
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<News> execute() {
        NewsClient newsClient = new NewsClient();
        ArrayList<News> news = new ArrayList<>();
        ArrayList<News> gNews = null, sportNews = null, cricNews = null, cnnNews = null;
        try {
            if (input.contains("news") || input.contains("feeds") || input.contains("breaking")) {
                gNews = newsClient.getGoogleNews();
                cnnNews = newsClient.getCNNNews();
            } else if (input.contains("cricket")) {
                cricNews = newsClient.getCricNews();
            } else if (input.contains("Sports".toLowerCase())) {
                sportNews = newsClient.getSportNews();
            }

            if (gNews != null && !gNews.isEmpty()) {
                news.addAll(gNews);
            }
            if (sportNews != null && !sportNews.isEmpty()) {
                news.addAll(sportNews);
            }
            if (cricNews != null && !cricNews.isEmpty()) {
                news.addAll(cricNews);
            }

            if (cnnNews != null && !cnnNews.isEmpty()) {
                news.addAll(cnnNews);
            }
            return news;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setQuery(String query) {
        this.input = query;
    }
}
