package com.codelab.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.codelab.chat.Models.FBHelper;
import com.codelab.chat.Models.User;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SIGN_IN = 2001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        ButterKnife.bind(this);


        checkUser();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);


    }

    private void checkUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            checkHasExtraInfo();
        } else {
            signin();
        }
    }

    private void checkHasExtraInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FBHelper.getReference("users/" + user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null)
                    startActivity(new Intent(SplashScreenActivity.this
                            , ProfileActivity.class));
                else
                    startActivity(new Intent(SplashScreenActivity.this
                            , ChatActivity.class));
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void signin() {
        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.mipmap.ic_launcher)
                .setTheme(R.style.AppTheme)
                .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                .build();

        startActivityForResult(intent, SIGN_IN);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
            }
            checkUser();
        }
    }
//    public void openLogin(View view) {
//        Intent intent = AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setLogo(R.mipmap.ic_launcher)
//                .setTheme(R.style.AppTheme)
//                .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
//                .build();
//
//        startActivityForResult(intent, SIGN_IN);
//
//
//    }
//

//
//    public void signOut(View view) {
//        AuthUI.getInstance()
//                .signOut(this)
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        checkUser();
//
//                    }
//                });
//    }


}
