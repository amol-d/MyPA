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
import com.msc.idol.mypa.R;
import com.msc.idol.mypa.chat.model.Message;
import com.msc.idol.mypa.model.news.News;
import com.msc.idol.mypa.model.quote.Quote;
import com.msc.idol.mypa.model.weather.Weather;
import com.msc.idol.mypa.model.web.WebResult;

import java.io.Serializable;
import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageItemViewHolder> {

    private ArrayList<Message> messageList;
    private Context context;

    public ChatAdapter(final ArrayList<Message> messages, Context context) {
        this.messageList = messages;
        this.context = context;
    }

    @Override
    public MessageItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.chat_item, viewGroup, false);
        return new MessageItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageItemViewHolder holder, int position) {
        Message message = messageList.get(position);

        final boolean isMe = message.isMine();
        final Object action = message.getData();
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            holder.parentLayput.setGravity(Gravity.END);
            holder.layout.setBackgroundResource(R.drawable.sender_msg_bubble2);
            holder.layout.setGravity(Gravity.END);
        } else {
            holder.parentLayput.setGravity(Gravity.START);
            holder.layout.setBackgroundResource(R.drawable.receiver_msg_bubble1);
            holder.layout.setGravity(Gravity.START);
        }
        holder.body.setText(message.getMessage());
        if (action == null) {
            holder.detailCardView.setVisibility(View.GONE);
        } else {
            holder.detailCardView.setVisibility(View.VISIBLE);
            addDetailCard(action, holder);
        }
    }

    private void addDetailCard(final Object output, MessageItemViewHolder holder) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.TRANSPARENT);
        if (output instanceof ArrayList) {
            if (!((ArrayList) output).isEmpty()) {
                Object o = ((ArrayList) output).get(0);
                if (o instanceof News) {
                    for (int i = 0; i < 3; i++) {
                        News news = (News) ((ArrayList) output).get(i);
                        View view = LayoutInflater.from(context).inflate(R.layout.news_item, null);
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
                        if (i == 2)
                            txtViewMore.setVisibility(View.VISIBLE);

                        txtViewMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ItemListActivity.class);
                                intent.putExtra(ItemListActivity.INTENT_NEWS_ITEMS, (Serializable) output);
                                context.startActivity(intent);
                            }
                        });
                        layout.addView(view);
                    }
                } else if (o instanceof WebResult) {

                    for (int i = 0; i < 3; i++) {
                        WebResult webResult = (WebResult) ((ArrayList) output).get(i);
                        View view = LayoutInflater.from(context).inflate(R.layout.news_item, null);
                        TextView txtTitle, txtDesc, txtUrl, txtViewMore;
                        ImageView imgNewsImage;
                        imgNewsImage = (ImageView) view.findViewById(R.id.imgNewsImage);
                        txtDesc = (TextView) view.findViewById(R.id.txtDesc);
                        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
                        txtUrl = (TextView) view.findViewById(R.id.txtUrl);
                        txtViewMore = (TextView) view.findViewById(R.id.txtViewMore);

                        imgNewsImage.setImageResource(R.drawable.cover);
                        txtDesc.setText(webResult.getText());
                        txtTitle.setText(webResult.getTitle());
                        txtUrl.setText(webResult.getUrl());
                        if (i == 2)
                            txtViewMore.setVisibility(View.VISIBLE);
                        txtViewMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ItemListActivity.class);
                                intent.putExtra(ItemListActivity.INTENT_WEB_ITEMS, (Serializable) output);
                                context.startActivity(intent);
                            }
                        });
                        layout.addView(view);
                    }
                }
            }
        } else if (output instanceof Weather) {
            TextView textView1 = new TextView(context);
            TextView textView2 = new TextView(context);
            TextView textView3 = new TextView(context);
            TextView textView4 = new TextView(context);
            TextView textView5 = new TextView(context);
            textView1.setText("City :" + ((Weather) output).getCityName());
            textView2.setText("Humidity : " + ((Weather) output).getHumidity());
            textView3.setText("Pressure : " + ((Weather) output).getPressure());
            textView4.setText("Min. Temp. : " + ((Weather) output).getTempMain());
            textView5.setText("Max. Temp. : " + ((Weather) output).getTempMax());

            layout.addView(textView1);
            layout.addView(textView2);
            layout.addView(textView3);
            layout.addView(textView4);
            layout.addView(textView5);
        } else if (output instanceof Quote) {
            TextView textView1 = new TextView(context);
            TextView textView2 = new TextView(context);
            TextView textView3 = new TextView(context);
            textView1.setText(((Quote) output).getTitle());
            textView2.setText(((Quote) output).getContent().replace("<p>", "").replaceAll("</p>", ""));
            textView3.setText(Html.fromHtml("<a href = " + ((Quote) output).getLink() + ">" + ((Quote) output).getLink() + "</a>"));
            layout.addView(textView1);
            layout.addView(textView2);
            layout.addView(textView3);
        }

        holder.detailCardView.addView(layout);
    }

    @Override
    public int getItemCount() {
        return this.messageList.size();
    }

    // This method is used to update data for adapter and notify adapter that data has changed
    public void updateList(ArrayList<Message> data) {
        messageList = data;
        notifyDataSetChanged();
    }

    public void addMessage(Message message) {
        messageList.add(message);
        notifyItemInserted(messageList.size() - 1);
    }

    public class MessageItemViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout, parentLayput;
        public TextView body;
        public CardView detailCardView;

        public MessageItemViewHolder(View itemView) {
            super(itemView);
            parentLayput = (LinearLayout) itemView.findViewById(R.id.bubble_layout_parent);
            layout = (LinearLayout) itemView.findViewById(R.id.bubble_layout);
            body = (TextView) itemView.findViewById(R.id.message_text);
            detailCardView = (CardView) itemView.findViewById(R.id.detail_card);
        }
    }
}