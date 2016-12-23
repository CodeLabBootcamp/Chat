package com.codelab.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
            startActivity(new Intent(this, AddExtraInfoActivity.class));
        } else {
            signin();
        }
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
