package com.codelab.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codelab.chat.Adapters.MessagesAdapter;
import com.codelab.chat.Models.FBHelper;
import com.codelab.chat.Models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.messages_list)
    RecyclerView messagesList;
    @BindView(R.id.message)
    EditText message;
    @BindView(R.id.send_message)
    ImageButton sendMessage;
    @BindView(R.id.activity_chat)
    LinearLayout activityChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        DatabaseReference ref = FBHelper.getReference("public");
        messagesList.setLayoutManager(new LinearLayoutManager(this));
        messagesList.setAdapter(new MessagesAdapter(this, ref));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String message = this.message.getText().toString();

        if (!message.isEmpty()) {
            Message m = new Message();
            m.setContent(message);
            m.setSenderId(user.getUid());
            m.setTimestamp(System.currentTimeMillis());
            DatabaseReference ref = FBHelper.getReference("public");
            ref.push().setValue(m);
            this.message.setText(null);
        } else
            Toast.makeText(this, "Cannot send an empty message", Toast.LENGTH_SHORT).show();
    }
}
