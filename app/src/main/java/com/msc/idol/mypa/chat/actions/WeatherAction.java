package com.msc.idol.mypa.chat.actions;

import com.msc.idol.mypa.MyPAApp;
import com.msc.idol.mypa.model.weather.Weather;
import com.msc.idol.mypa.network.WeatherClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by adesai on 3/15/2017.
 */

public class WeatherAction implements Action {
    String input;

    @Override
    public ArrayList<String> getRecognizer() {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("weather");
        queries.add("feeds");
        queries.add("breaking");
        return queries;
    }

    @Override
    public boolean isActionFitting(String input) {
        for (String q : getRecognizer()) {
            if (q.toLowerCase().contains(input.toLowerCase())) {
                this.input = input;
                return true;
            }
        }
        return false;
    }

    @Override
    public Weather execute() {
        WeatherClient weatherClient = new WeatherClient();

        try {
            return weatherClient.getWeatherForLatLon(MyPAApp.getLat(), MyPAApp.getLng());
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
