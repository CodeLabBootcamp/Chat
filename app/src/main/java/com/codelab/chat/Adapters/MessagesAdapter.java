package com.codelab.chat.Adapters;

import android.content.Context;
import android.graphics.Color;

import com.codelab.chat.Models.Message;
import com.codelab.chat.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessagesAdapter extends FirebaseRecyclerAdapter<Message, MessagesViewHolder> {

    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public MessagesAdapter(Context context, DatabaseReference ref) {
        super(Message.class, R.layout.item_chat, MessagesViewHolder.class, ref);
        this.context = context;
    }


    @Override
    protected void populateViewHolder(MessagesViewHolder holder, Message message, int position) {
        holder.messageContent.setText(message.getContent());
        if (message.getSenderId().equals(user.getUid())) {
            holder.messageContent.setTextColor(Color.BLUE);
        } else {
            holder.messageContent.setTextColor(Color.BLACK);
        }
    }

}
