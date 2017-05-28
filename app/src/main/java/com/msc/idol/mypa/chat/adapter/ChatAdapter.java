package com.msc.idol.mypa.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msc.idol.mypa.ItemListActivity;
import com.msc.idol.mypa.MyPAApp;
import com.msc.idol.mypa.R;
import com.msc.idol.mypa.model.message.MsgTable;
import com.msc.idol.mypa.model.news.News;
import com.msc.idol.mypa.model.quote.Quote;
import com.msc.idol.mypa.model.string.StringOP;
import com.msc.idol.mypa.model.weather.Weather;
import com.msc.idol.mypa.model.web.WebResult;

import java.util.ArrayList;

import io.realm.Realm;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageItemViewHolder> {

    private final Realm realm;
    private ArrayList<com.msc.idol.mypa.model.message.Message> messageList;
    private Context context;

    public ChatAdapter(final ArrayList<com.msc.idol.mypa.model.message.Message> messages, Context context) {
        this.messageList = messages;
        this.context = context;
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
    }

    @Override
    public MessageItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.chat_item, viewGroup, false);
        return new MessageItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageItemViewHolder holder, int position) {
        com.msc.idol.mypa.model.message.Message message = messageList.get(position);

        final boolean isMe = message.isMine();
        final Object action = message.getData();
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            holder.parentLayput.setGravity(Gravity.END);
            holder.layout.setBackgroundResource(R.drawable.sender_msg_bubble2);
            holder.layout.setGravity(Gravity.END);
            holder.detailCardView.setVisibility(View.GONE);
        } else {
            holder.parentLayput.setGravity(Gravity.START);
            holder.layout.setBackgroundResource(R.drawable.receiver_msg_bubble1);
            holder.layout.setGravity(Gravity.START);
            holder.detailCardView.setVisibility(View.GONE);
            if (action == null) {
                holder.detailCardView.setVisibility(View.GONE);
            } else {
                holder.detailCardView.setVisibility(View.VISIBLE);
                addDetailCard(action, holder);
            }
        }
        holder.body.setText(message.getMessage());

    }

    private void addDetailCard(final Object output, MessageItemViewHolder holder) {
        if (output instanceof ArrayList) {
            if (!((ArrayList) output).isEmpty()) {
                Object o = ((ArrayList) output).get(0);
                if (o instanceof News) {
                    for (int i = 0; i < 3; i++) {
                        News news = (News) ((ArrayList) output).get(i);
                        holder.imgNewsImage.setImageResource(R.drawable.cover);
                        holder.txtTitle.setText(news.getTitle());
                        holder.txtUrl.setText(news.getUrl());
                        if (i == 2)
                            holder.txtViewMore.setVisibility(View.VISIBLE);

                        holder.txtViewMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ItemListActivity.class);
                                MyPAApp.getInstance().setNewsList((ArrayList<News>) output);
                                MyPAApp.getInstance().setWebResults(null);
                                context.startActivity(intent);
                            }
                        });
                    }
                } else if (o instanceof WebResult) {

                    for (int i = 0; i < 3; i++) {
                        WebResult webResult = (WebResult) ((ArrayList) output).get(i);
                        holder.imgNewsImage.setImageResource(R.drawable.cover);
                        holder.txtTitle.setText(webResult.getTitle());
                        holder.txtUrl.setText(webResult.getUrl());
                        if (i == 2)
                            holder.txtViewMore.setVisibility(View.VISIBLE);
                        holder.txtViewMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ItemListActivity.class);
                                MyPAApp.getInstance().setWebResults((ArrayList<WebResult>) output);
                                MyPAApp.getInstance().setNewsList(null);
                                context.startActivity(intent);
                            }
                        });
                    }
                }
            }
        } else if (output instanceof Weather) {
            holder.txtTitle.setText("City :" + ((Weather) output).getCityName());
            holder.txtDesc.setText("Humidity : " + ((Weather) output).getHumidity());
            holder.txtUrl.setText("Pressure : " + ((Weather) output).getPressure());
            holder.txt4.setText("Min. Temp. : " + ((Weather) output).getTempMain());
            holder.txt5.setText("Max. Temp. : " + ((Weather) output).getTempMax());
        } else if (output instanceof Quote) {
            holder.txtTitle.setText(((Quote) output).getTitle());
            holder.txtDesc.setText(((Quote) output).getContent().replace("<p>", "").replaceAll("</p>", ""));
            holder.txtUrl.setText(Html.fromHtml("<a href = " + ((Quote) output).getLink() + ">" + ((Quote) output).getLink() + "</a>"));
        } else if (output instanceof StringOP) {

        }
    }

    @Override
    public int getItemCount() {
        return this.messageList.size();
    }

    // This method is used to update data for adapter and notify adapter that data has changed
    public void updateList(ArrayList<com.msc.idol.mypa.model.message.Message> data) {
        messageList = data;
        notifyDataSetChanged();
    }

    public void addMessage(com.msc.idol.mypa.model.message.Message message) {
        realm.beginTransaction();

        Number currentIdNum = realm.where(MsgTable.class).max("id");
        int nextId;
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }

        message.setId(nextId);
        MsgTable msgTable = new MsgTable(message.getMessage(), message.isMine());
        msgTable.setId(message.getId());
        realm.copyToRealmOrUpdate(msgTable);

        Object output = message.getData();
        if (output instanceof String) {
            StringOP stringOP = new StringOP(nextId, (String) output);
            realm.copyToRealm(stringOP);
        } else if (output instanceof ArrayList) {
            if (!((ArrayList) output).isEmpty()) {
                Object o = ((ArrayList) output).get(0);
                if (o instanceof News) {
                    for (News news : (ArrayList<News>) output) {
                        news.setId(nextId);
                        realm.copyToRealm(news);
                    }
                } else if (o instanceof WebResult) {
                    for (WebResult webResult : (ArrayList<WebResult>) output) {
                        webResult.setId(nextId);
                        realm.copyToRealm(webResult);
                    }
                }
            }
        } else if (output instanceof Weather) {
            Weather weather = (Weather) output;
            weather.setId(nextId);
            realm.copyToRealm(weather);
        } else if (output instanceof Quote) {
            Quote quote = (Quote) output;
            quote.setId(nextId);
            realm.copyToRealm(quote);
        }


        realm.commitTransaction();


        messageList.add(message);
        notifyItemInserted(messageList.size() - 1);
    }

    public class MessageItemViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout, parentLayput;
        public TextView body;
        public CardView detailCardView;
        TextView txtTitle, txtDesc, txtUrl, txtViewMore, txt4, txt5;
        ImageView imgNewsImage;

        public MessageItemViewHolder(View itemView) {
            super(itemView);
            parentLayput = (LinearLayout) itemView.findViewById(R.id.bubble_layout_parent);
            layout = (LinearLayout) itemView.findViewById(R.id.bubble_layout);
            body = (TextView) itemView.findViewById(R.id.message_text);
            detailCardView = (CardView) itemView.findViewById(R.id.detail_card);
            imgNewsImage = (ImageView) itemView.findViewById(R.id.imgNewsImage);
            txtDesc = (TextView) itemView.findViewById(R.id.txtDesc);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtUrl = (TextView) itemView.findViewById(R.id.txtUrl);
            txtViewMore = (TextView) itemView.findViewById(R.id.txtViewMore);
            txt4 = (TextView) itemView.findViewById(R.id.txt4);
            txt5 = (TextView) itemView.findViewById(R.id.txt5);
        }
    }
}