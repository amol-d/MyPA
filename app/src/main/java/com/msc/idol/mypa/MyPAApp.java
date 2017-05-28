package com.msc.idol.mypa;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.msc.idol.mypa.model.news.News;
import com.msc.idol.mypa.model.web.WebResult;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by adesai on 3/15/2017.
 */
public class MyPAApp extends Application {
    public static float lat, lng;

    private static MyPAApp mInstance;

    ArrayList<News> newsList;
    ArrayList<WebResult> webResults;

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        mInstance = this;
        // Initialize Realm
        Realm.init(mInstance);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }

    public static MyPAApp getInstance() {
        return mInstance;
    }

    public Context getContext() {
        return this.getApplicationContext();
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

    public static void setmInstance(MyPAApp mInstance) {
        MyPAApp.mInstance = mInstance;
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<News> newsList) {
        this.newsList = newsList;
    }

    public ArrayList<WebResult> getWebResults() {
        return webResults;
    }

    public void setWebResults(ArrayList<WebResult> webResults) {
        this.webResults = webResults;
    }
}
