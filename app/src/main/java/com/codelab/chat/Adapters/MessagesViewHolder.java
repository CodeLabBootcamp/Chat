package com.codelab.chat.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.codelab.chat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.sender_name)
    TextView senderName;
    @BindView(R.id.message_content)
    TextView messageContent;
    @BindView(R.id.timestamp)
    TextView timestamp;

    public MessagesViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

}
