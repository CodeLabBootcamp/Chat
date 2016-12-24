package com.codelab.chat.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.codelab.chat.Models.FBHelper;
import com.codelab.chat.Models.Message;
import com.codelab.chat.Models.User;
import com.codelab.chat.R;
import com.codelab.chat.Utilities.Tools;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessagesAdapter extends FirebaseRecyclerAdapter<Message, MessagesViewHolder> {

    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public MessagesAdapter(Context context, DatabaseReference ref) {
        super(Message.class, R.layout.item_chat, MessagesViewHolder.class, ref);
        this.context = context;
    }


    @Override
    protected void populateViewHolder(final MessagesViewHolder holder, Message message, int position) {

        holder.messageContent.setText(message.getContent());
        holder.timestamp.setText(Tools.formatTimestamp(message.getTimestamp()));

        DatabaseReference ref = FBHelper.getReference("users/" + message.getSenderId());

        holder.senderName.setText("");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                holder.senderName.setText(user.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        if (message.getSenderId().equals(user.getUid())) {
            params.gravity = Gravity.END;
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card_background_color));
        } else {
            params.gravity = Gravity.START;
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }

        holder.cardView.setLayoutParams(params);

    }

}
