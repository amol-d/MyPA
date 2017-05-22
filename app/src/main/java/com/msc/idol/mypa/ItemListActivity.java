package com.msc.idol.mypa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msc.idol.mypa.model.news.News;
import com.msc.idol.mypa.model.web.WebResult;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    public static final String INTENT_NEWS_ITEMS = "key_news_items";
    public static final String INTENT_WEB_ITEMS = "key_web_items";
    ArrayList<News> newsList;
    ArrayList<WebResult> webResults;
    LinearLayout rootLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rootLayout = (LinearLayout) findViewById(R.id.rootContainer);
        newsList = (ArrayList<News>) getIntent().getSerializableExtra(INTENT_NEWS_ITEMS);
        webResults = (ArrayList<WebResult>) getIntent().getSerializableExtra(INTENT_WEB_ITEMS);
        if (newsList != null) {
            addNewsItems(newsList);
        } else if (webResults != null) {

        }
    }

    private void addNewsItems(ArrayList<News> newsList) {
        for (News news : newsList) {
            View view = LayoutInflater.from(this).inflate(R.layout.news_item, null);
            TextView txtTitle, txtDesc, txtUrl, txtViewMore;
            ImageView imgNewsImage;
            imgNewsImage = (ImageView) view.findViewById(R.id.imgNewsImage);
            txtDesc = (TextView) view.findViewById(R.id.txtDesc);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtUrl = (TextView) view.findViewById(R.id.txtUrl);
            txtViewMore = (TextView) view.findViewById(R.id.txtViewMore);

            imgNewsImage.setImageResource(R.drawable.cover);
//                        txtDesc.setText(news.getDesc());
            txtTitle.setText(news.getTitle());
            txtUrl.setText(news.getUrl());

            rootLayout.addView(view);
        }
    }
}
