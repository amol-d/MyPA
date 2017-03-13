package com.msc.idol.mypa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.msc.idol.mypa.chat.adapter.ChatAdapter;
import com.msc.idol.mypa.chat.model.Message;
import com.msc.idol.mypa.chat.service.ChatService;
import com.msc.idol.mypa.chat.utils.Constants;
import com.msc.idol.mypa.chat.utils.Util;
import com.msc.idol.mypa.model.weather.WeatherClient;
import com.msc.idol.mypa.model.web.WebUtils;
import com.msc.idol.mypa.model.web.WebhoseIOClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPAActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ResponseReceiver receiver;
    private Handler handler;
    private EditText etMessage;
    private ImageButton btSend;
    private RecyclerView rvChat;
    private ArrayList<Message> mMessages;
    private ChatAdapter chatRecyclerAdapter;
    // Defines a runnable which is run every 100ms
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            chatRecyclerAdapter.addMessage(Util.getNewMessage());
            rvChat.scrollToPosition(chatRecyclerAdapter.getItemCount() - 1);
            handler.postDelayed(this, 5000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            WebhoseIOClient webhoseClient = WebhoseIOClient.getInstance(WebUtils.WEBHOSE_API_KEY);
                            Map<String, String> queries = new HashMap<String, String>();
                            queries.put("q", "foobar");

                            JsonElement result = webhoseClient.query("filterWebData", "IndVSAus");

                            System.out.println("Total results  = " + result.getAsJsonObject().get("totalResults"));     // Print posts count

                            JsonArray postArray = result.getAsJsonObject().getAsJsonArray("posts");

                            for (JsonElement o : postArray) {
                                System.out.println(o.getAsJsonObject().get("title"));  // Print title
                                System.out.println(o.getAsJsonObject().get("author")); // Print author
                                System.out.println(o.getAsJsonObject().get("text"));   // Print text
                                System.out.println(o.getAsJsonObject().get("url"));   // Print url
                                System.out.println(o.getAsJsonObject().get("language"));   // Print language
                            }

                            WeatherClient weatherClient = new WeatherClient();
                            JsonObject mainWeather = weatherClient.getWeatherForCity("Mumbai").getAsJsonObject().getAsJsonObject("main");
                            System.out.println("temp = kelvin " + mainWeather.get("temp"));
                            System.out.println("temp min = kelvin " + mainWeather.get("temp_min"));
                            System.out.println("temp max = kelvin " + mainWeather.get("temp_max"));
                            System.out.println("pressure = hPa " + mainWeather.get("pressure"));
                            System.out.println("humidity = % " + mainWeather.get("humidity"));

                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(runnable).start();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        etMessage = (EditText) findViewById(R.id.messageEditText);
        btSend = (ImageButton) findViewById(R.id.sendMessageButton);
        rvChat = (RecyclerView) findViewById(R.id.rvChat);

        // Setting the LayoutManager.
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        //Set LayoutManager to RecyclerView
        rvChat.setLayoutManager(layoutManager);

        // initialize the adapter
        mMessages = new ArrayList<Message>();

        chatRecyclerAdapter = new ChatAdapter(mMessages);
        // attach the adapter to the RecyclerView
        rvChat.setAdapter(chatRecyclerAdapter);

        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendMessage(etMessage.getText().toString().trim());
            }
        });
    }

    private void sendMessage(String trim) {
        //TODO send message
    }

    @Override
    protected void onResume() {
        super.onResume();
        // create intent filter and register the broadcast receiver for the chat service
        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        handler = new Handler();
        refreshMessages();

        // Run the runnable object defined every 100ms
        handler.postDelayed(runnable, 5000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        handler = null;
    }

    private void refreshMessages() {
        // start intent service
        Intent msgIntent = new Intent(this, ChatService.class);
        //TODO send authentication and other required info.
        startService(msgIntent);
    }

    // Broadcast receiver that will receive data from service
    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP = "action_msgs_response";

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Message> messages = (ArrayList<Message>) intent.getSerializableExtra(Constants.INTENT_MSGS_EXTRA);
            chatRecyclerAdapter.updateList(messages);
            rvChat.scrollToPosition(messages.size() - 1);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_pa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            startActivity(new Intent(getBaseContext(), DetailsActivity.class));
        } else if (id == R.id.settings) {

        } else if (id == R.id.share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
