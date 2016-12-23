package com.codelab.chat.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.codelab.chat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.message_content)
    TextView messageContent;

    public MessagesViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

}
