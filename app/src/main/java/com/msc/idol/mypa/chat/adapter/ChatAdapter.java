package com.msc.idol.mypa.chat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msc.idol.mypa.R;
import com.msc.idol.mypa.chat.model.Message;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageItemViewHolder> {

    private ArrayList<Message> messageList;

    public ChatAdapter(final ArrayList<Message> messages) {
        this.messageList = messages;
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

        public MessageItemViewHolder(View itemView) {
            super(itemView);
            parentLayput = (LinearLayout) itemView.findViewById(R.id.bubble_layout_parent);
            layout = (LinearLayout) itemView.findViewById(R.id.bubble_layout);
            body = (TextView) itemView.findViewById(R.id.message_text);
        }

    }
}