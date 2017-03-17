package com.msc.idol.mypa;

import android.app.Application;

/**
 * Created by adesai on 3/15/2017.
 */
public class MyPAApp extends Application {
    public static float lat, lng;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static float getLat() {
        return lat;
    }

    public static void setLat(float lat) {
        MyPAApp.lat = lat;
    }

    public static float getLng() {
        return lng;
    }

    public static void setLng(float lng) {
        MyPAApp.lng = lng;
    }
}
