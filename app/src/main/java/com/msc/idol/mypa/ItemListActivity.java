package com.msc.idol.mypa;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msc.idol.mypa.model.news.News;
import com.msc.idol.mypa.model.web.WebResult;
import com.squareup.picasso.Picasso;

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
        newsList = MyPAApp.getInstance().getNewsList();//(ArrayList<News>) getIntent().getSerializableExtra(INTENT_NEWS_ITEMS);
        webResults = MyPAApp.getInstance().getWebResults();//(ArrayList<WebResult>) getIntent().getSerializableExtra(INTENT_WEB_ITEMS);
        if (newsList != null) {
            addNewsItems(newsList);
        } else if (webResults != null) {
            addWebItems(webResults);
        }
    }

    private void addWebItems(ArrayList<WebResult> webResults) {
        for (WebResult webResult : webResults) {
            View view = LayoutInflater.from(this).inflate(R.layout.news_item, null);
            TextView txtTitle, txtDesc, txtUrl, txtViewMore;
            ImageView imgNewsImage;
            imgNewsImage = (ImageView) view.findViewById(R.id.imgNewsImage);
            txtDesc = (TextView) view.findViewById(R.id.txtDesc);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtUrl = (TextView) view.findViewById(R.id.txtUrl);
            txtViewMore = (TextView) view.findViewById(R.id.txtViewMore);

            imgNewsImage.setVisibility(View.GONE);
            //Picasso.with(getBaseContext()).load(webResult.getImageUrl()).centerInside().into(imgNewsImage);
//                        txtDesc.setText(news.getDesc());
            txtTitle.setText(webResult.getTitle());
            txtUrl.setText(webResult.getUrl());

            View view1 = new View(getBaseContext());
            view1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            view1.setBackgroundColor(Color.BLACK);
            rootLayout.addView(view);
            rootLayout.addView(view1);
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
            Picasso.with(getBaseContext()).load(news.getImageUrl()).resize(60, 60).into(imgNewsImage);
//                        txtDesc.setText(news.getDesc());
            txtTitle.setText(news.getTitle());
            txtUrl.setText(news.getUrl());

            View view1 = new View(getBaseContext());
            view1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            view1.setBackgroundColor(Color.BLACK);
            rootLayout.addView(view);
            rootLayout.addView(view1);
        }
    }
}
