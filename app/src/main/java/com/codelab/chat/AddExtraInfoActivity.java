package com.codelab.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.codelab.chat.Models.FBHelper;
import com.codelab.chat.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddExtraInfoActivity extends AppCompatActivity {

    @BindView(R.id.age)
    EditText age;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;
    @BindView(R.id.other)
    RadioButton other;
    @BindView(R.id.gender)
    RadioGroup gender;
    @BindView(R.id.activity_add_extra_info)
    LinearLayout activityAddExtraInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_extra_info);
        ButterKnife.bind(this);
    }

    public void save(View view) {

        if (checkEverything()) {
            FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
            User user = new User();
            user.setId(fbUser.getUid());
            user.setName(fbUser.getDisplayName());
            user.setEmail(fbUser.getEmail());
            user.setAddress(address.getText().toString());
            user.setAge(Integer.parseInt(age.getText().toString()));
            user.setGender(this, getSelectedGenderText());

            DatabaseReference ref = FBHelper.getReference("users/" + fbUser.getUid());
            ref.setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(new Intent(AddExtraInfoActivity.this
                            , ChatActivity.class));
                    finish();
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddExtraInfoActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Check all fields", Toast.LENGTH_SHORT).show();
        }

    }


    private boolean checkEverything() {
        return checkNotEmpty(age, address) && gender.getCheckedRadioButtonId() != -1;
    }

    private boolean checkNotEmpty(EditText... editTexts) {
        for (EditText et : editTexts) {
            if (et.getText().toString().isEmpty())
                return false;
        }

        return true;
    }

    public String getSelectedGenderText() {
        RadioButton selectedRadioButton = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
        return selectedRadioButton.getText().toString();
    }
}
