package com.msc.idol.mypa;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.msc.idol.mypa.chat.actions.ActionManager;
import com.msc.idol.mypa.chat.adapter.ChatAdapter;
import com.msc.idol.mypa.chat.utils.Constants;
import com.msc.idol.mypa.chat.voice.VoiceResponse;
import com.msc.idol.mypa.location.LocationTracker;
import com.msc.idol.mypa.model.message.Message;
import com.msc.idol.mypa.model.message.MsgTable;
import com.msc.idol.mypa.model.news.News;
import com.msc.idol.mypa.model.quote.Quote;
import com.msc.idol.mypa.model.string.StringOP;
import com.msc.idol.mypa.model.weather.Weather;
import com.msc.idol.mypa.model.web.WebResult;
import com.msc.idol.mypa.model.web.WebUtils;
import com.msc.idol.mypa.network.NewsClient;
import com.msc.idol.mypa.network.QuoteClient;
import com.msc.idol.mypa.network.WeatherClient;
import com.msc.idol.mypa.network.WebhoseIOClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyPAActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SoftKeyboardRelativeLayout.SoftKeyboardListener, AssistantResponseReceiver {

    private static final String TAG = MyPAActivity.class.getSimpleName();
    private ResponseReceiver receiver;
    private Handler handler;
    private EditText etMessage;
    private ImageButton btSend;
    private RecyclerView rvChat;
    private ArrayList<com.msc.idol.mypa.model.message.Message> mMessages;
    private ChatAdapter chatRecyclerAdapter;
    private static final String[] PROCESS_LOCATION_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int PROCESS_REQUEST_CODE = 2002;
    private VoiceResponse voice;
    private SoftKeyboardRelativeLayout softKeyboardRelativeLayout;
    ActionManager actionManager;
    protected static final int RESULT_SPEECH = 1;


    private boolean voiceCommand = true;
    private Realm realm;
    private RealmResults<MsgTable> resultMsgTbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionManager = new ActionManager();
        voice = new VoiceResponse(getApplicationContext());
        // Initialize Realm
        realm = Realm.getDefaultInstance();
        softKeyboardRelativeLayout = (SoftKeyboardRelativeLayout) findViewById(R.id.content_my_pa);
        softKeyboardRelativeLayout.addSoftKeyboardListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            WebhoseIOClient webhoseClient = WebhoseIOClient.getInstance(WebUtils.WEBHOSE_API_KEY);
                            ArrayList<WebResult> result = webhoseClient.query("filterWebData", "IndVSAus");

                            System.out.println("Total results  = " + result.size());     // Print posts count


                            for (WebResult o : result) {
                                System.out.println(o.getTitle());  // Print title
                                System.out.println(o.getAuthor()); // Print author
                                System.out.println(o.getText());   // Print text
                                System.out.println(o.getUrl());   // Print url
                                System.out.println(o.getLanguage());   // Print language
                            }

                            WeatherClient weatherClient = new WeatherClient();
                            Weather mainWeather = weatherClient.getWeatherForCity("Mumbai");
                            System.out.println("City = " + mainWeather.getCityName());
                            System.out.println("temp = kelvin " + mainWeather.getTempMain());
                            System.out.println("temp min = kelvin " + mainWeather.getTempMin());
                            System.out.println("temp max = kelvin " + mainWeather.getTempMax());
                            System.out.println("pressure = hPa " + mainWeather.getPressure());
                            System.out.println("humidity = % " + mainWeather.getHumidity());


                            mainWeather = weatherClient.getWeatherForLatLon(MyPAApp.getLat(), MyPAApp.getLng());
                            System.out.println("City = " + mainWeather.getCityName());
                            System.out.println("temp = kelvin " + mainWeather.getTempMain());
                            System.out.println("temp min = kelvin " + mainWeather.getTempMin());
                            System.out.println("temp max = kelvin " + mainWeather.getTempMax());
                            System.out.println("pressure = hPa " + mainWeather.getPressure());
                            System.out.println("humidity = % " + mainWeather.getHumidity());

                            NewsClient newsClient = new NewsClient();
                            ArrayList<News> googleNews = newsClient.getGoogleNews();
                            ArrayList<News> toiNews = newsClient.getTOINews();
                            ArrayList<News> cnnNews = newsClient.getCNNNews();
                            ArrayList<News> cricNews = newsClient.getCricNews();
                            ArrayList<News> sportsNews = newsClient.getSportNews();

                            for (News element : googleNews) {
                                System.out.println(element.getTitle());
                                System.out.println(element.getDesc());
                                System.out.println(element.getUrl());
                                System.out.println(element.getImageUrl());
                            }

                            for (News element : toiNews) {
                                System.out.println(element.getTitle());
                                System.out.println(element.getDesc());
                                System.out.println(element.getUrl());
                                System.out.println(element.getImageUrl());
                            }

                            for (News element : cnnNews) {
                                System.out.println(element.getTitle());
                                System.out.println(element.getDesc());
                                System.out.println(element.getUrl());
                                System.out.println(element.getImageUrl());
                            }

                            for (News element : cricNews) {
                                System.out.println(element.getTitle());
                                System.out.println(element.getDesc());
                                System.out.println(element.getUrl());
                                System.out.println(element.getImageUrl());
                            }

                            for (News element : sportsNews) {
                                System.out.println(element.getTitle());
                                System.out.println(element.getDesc());
                                System.out.println(element.getUrl());
                                System.out.println(element.getImageUrl());
                            }

                            QuoteClient quoteClient = new QuoteClient();
                            quoteClient.getQuote();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
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

        btSend.setBackgroundResource(R.drawable.ic_mic_white_18dp);
        voiceCommand = true;

        // Setting the LayoutManager.
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        //Set LayoutManager to RecyclerView
        rvChat.setLayoutManager(layoutManager);


        // initialize the adapter
        mMessages = new ArrayList<com.msc.idol.mypa.model.message.Message>();
        resultMsgTbl = realm.where(MsgTable.class).findAll();
        for (MsgTable msgTable : resultMsgTbl) {
            Message message = new Message();
            message.setId(msgTable.getId());
            message.setIsMine(msgTable.isMine());
            message.setMessage(msgTable.getMessage());

            Object action = getActionForMessage(msgTable.getId());
            message.setAction(action);
            mMessages.add(message);
        }

        chatRecyclerAdapter = new ChatAdapter(mMessages, MyPAActivity.this);
        // attach the adapter to the RecyclerView
        rvChat.setAdapter(chatRecyclerAdapter);

        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!voiceCommand) {
                    if (!TextUtils.isEmpty(etMessage.getText().toString())) {
                        String input = etMessage.getText().toString();
                        chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(input, null, true));
                        //TODO add to conversation
                        Object output = actionManager.execute(MyPAActivity.this, etMessage.getText().toString(), MyPAActivity.this);
                        System.out.println(output);
                        String outputMessage = "Here's what I got for you";
                        if (output instanceof String)
                            voice.speech((String) output);
                        else
                            voice.speech(outputMessage);

                        if (output instanceof String) {
                            chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message((String) output, null, false));
                        } else if (output instanceof ArrayList) {
                            if (!((ArrayList) output).isEmpty()) {
                                Object o = ((ArrayList) output).get(0);
                                if (o instanceof News) {
                                    chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
                                } else if (o instanceof WebResult) {
                                    chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
                                }
                            }
                        } else if (output instanceof Weather) {
                            chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
                        } else if (output instanceof Quote) {
                            chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
                        }
                    }
                } else {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                    try {
                        startActivityForResult(intent, RESULT_SPEECH);
                    } catch (ActivityNotFoundException a) {
                        Toast t = Toast.makeText(getApplicationContext(), "Speech not supported", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // You need to ask the user to enable the permissions
            ActivityCompat.requestPermissions(this, PROCESS_LOCATION_PERMISSIONS, PROCESS_REQUEST_CODE);
        } else {
            getLocation();
        }

    }

    private Object getActionForMessage(long id) {
        StringOP stringAction = realm.where(StringOP.class).equalTo("id", id).findFirst();
        RealmResults<News> newsAtions = realm.where(News.class).equalTo("id", id).findAll();
        Quote quoteAction = realm.where(Quote.class).equalTo("id", id).findFirst();
        Weather weatherAction = realm.where(Weather.class).equalTo("id", id).findFirst();
        RealmResults<WebResult> webAction = realm.where(WebResult.class).equalTo("id", id).findAll();

        if (stringAction != null)
            return stringAction;
        else if (quoteAction != null)
            return quoteAction;
        else if (weatherAction != null)
            return weatherAction;
        else if (newsAtions != null) {
            ArrayList<News> newsArrayList = new ArrayList<>();
            for (News news : newsAtions)
                newsArrayList.add(news);
            return newsArrayList;
        } else if (webAction != null) {
            ArrayList<WebResult> webResults = new ArrayList<>();
            for (WebResult webResult : webAction)
                webResults.add(webResult);
            return webResults;
        }
        return "Waiting for message";
    }

    private void sendMessage(String trim) {
        //TODO send message
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PROCESS_REQUEST_CODE) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                getLocation();
            } else {
                Snackbar.make(rvChat,
                        "Location permission was not granted.",
                        Snackbar.LENGTH_LONG)
                        .show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        handler = null;
    }


    public void getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationTracker tracker = new LocationTracker(MyPAActivity.this) {
                @Override
                public void onLocationFound(Location location) {
                    MyPAApp.setLat((float) location.getLatitude());
                    MyPAApp.setLng((float) location.getLongitude());
                }

                @Override
                public void onTimeout() {
                    //Do nothing
                }
            };
            tracker.startListening();
        }
    }

    @Override
    public void onSoftKeyboardShow() {
        Log.i(TAG, "onSoftKeyboardShow: ");
        btSend.setBackgroundResource(R.drawable.button_send);
        voiceCommand = false;
    }

    @Override
    public void onSoftKeyboardHide() {
        Log.i(TAG, "onSoftKeyboardHide: ");
        btSend.setBackgroundResource(R.drawable.ic_mic_white_18dp);
        voiceCommand = true;
    }

    @Override
    public void responseReceived(Object output) {
        String outputMessage = "Here's what I got for you";
        if (output instanceof String)
            voice.speech((String) output);
        else
            voice.speech(outputMessage);
        if (output instanceof String) {
            chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message((String) output, null, false));
        } else if (output instanceof ArrayList) {
            if (!((ArrayList) output).isEmpty()) {
                Object o = ((ArrayList) output).get(0);
                if (o instanceof News) {
                    chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
                } else if (o instanceof WebResult) {
                    chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
                }
            }
        } else if (output instanceof Weather) {
            chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
        } else if (output instanceof Quote) {
            chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
        }
    }

    // Broadcast receiver that will receive data from service
    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP = "action_msgs_response";

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<com.msc.idol.mypa.model.message.Message> messages = (ArrayList<com.msc.idol.mypa.model.message.Message>) intent.getSerializableExtra(Constants.INTENT_MSGS_EXTRA);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String input = text.get(0);


                    //TODO handle output
                    Object output = actionManager.execute(MyPAActivity.this, input, MyPAActivity.this);
                    chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(input, null, true));
//                    Object output = actionManager.execute(input);
                    System.out.println(output.toString());
                    String outputMessage = "Here's what I got for you";
                    if (output instanceof String)
                        voice.speech((String) output);
                    else
                        voice.speech(outputMessage);
                    if (output instanceof String) {
                        chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message((String) output, null, false));
                    } else if (output instanceof ArrayList) {
                        if (!((ArrayList) output).isEmpty()) {
                            Object o = ((ArrayList) output).get(0);
                            if (o instanceof News) {
                                chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
                            } else if (o instanceof WebResult) {
                                chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
                            }
                        }
                    } else if (output instanceof Weather) {
                        chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
                    } else if (output instanceof Quote) {
                        chatRecyclerAdapter.addMessage(new com.msc.idol.mypa.model.message.Message(outputMessage, output, false));
                    }
                }
                break;
            }
        }
    }


}
