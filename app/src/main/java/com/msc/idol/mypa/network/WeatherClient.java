package com.msc.idol.mypa.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.msc.idol.mypa.model.weather.Weather;
import com.msc.idol.mypa.model.weather.WeatherUtils;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by amdam_000 on 3/13/2017.
 */

public class WeatherClient extends Client {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?appid=%s";
    public static final String GET_CITY_API = "http://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&sensor=true";

    public Weather getWeatherForLatLon(float lat, float lon) throws IOException, URISyntaxException {
        String url = String.format(BASE_URL + "&lat=%s&lon=%s", WeatherUtils.API_KEY, lat, lon);


        return getWeatherForJson(getResponse(url));
    }


    public Weather getWeatherForCity(String city) throws IOException, URISyntaxException {
        String url = String.format(BASE_URL + "&q=%s", WeatherUtils.API_KEY, city);

        JsonElement element = getResponse(url);
        return getWeatherForJson(element);
    }

    private Weather getWeatherForJson(JsonElement element) {
        JsonObject mainWeather = element.getAsJsonObject().getAsJsonObject("main");
        Weather weather = new Weather(element.getAsJsonObject().get("name").getAsString(), mainWeather.get("temp").getAsDouble(), mainWeather.get("temp_min").getAsDouble(), mainWeather.get("temp_max").getAsDouble(), mainWeather.get("humidity").getAsDouble(), mainWeather.get("pressure").getAsDouble());
        return weather;
    }
}
