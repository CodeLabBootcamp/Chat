package com.codelab.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.codelab.chat.Models.FBHelper;
import com.codelab.chat.Models.User;
import com.codelab.chat.Utilities.Gender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;

public class ProfileActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 2132;

    @BindView(R.id.age)
    TextInputEditText age;
    @BindView(R.id.address)
    TextInputEditText address;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;
    @BindView(R.id.other)
    RadioButton other;
    @BindView(R.id.gender)
    RadioGroup gender;
    @BindView(R.id.activity_add_extra_info)
    FrameLayout activityAddExtraInfo;
    String avatar;

    boolean isEdit;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FBHelper.getReference("users/" + user.getUid());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                isEdit = user != null;
                age.setText(String.valueOf(user.getAge()));
                address.setText(user.getAddress());
                avatar = user.getAvatar();

                refreshAvatar();

                switch (user.getGender()) {
                    case Gender.MALE:
                        male.setChecked(true);
                        break;
                    case Gender.FEMALE:
                        female.setChecked(true);
                        break;
                    case Gender.OTHER:
                        other.setChecked(true);
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void refreshAvatar() {
        Picasso.with(ProfileActivity.this)
                .load(avatar)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .into(profileImage);
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
            user.setAvatar(avatar);

            DatabaseReference ref = FBHelper.getReference("users/" + fbUser.getUid());
            ref.setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    if (!isEdit)
                        startActivity(new Intent(ProfileActivity.this
                                , ChatActivity.class));
                    finish();
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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

    public void uploadImage(View view) {

        EasyImage.openCamera(this, EasyImageConfig.REQ_TAKE_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {

            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                uploadImage(imageFile);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });

    }

    private void uploadImage(File imageFile) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference("image/" + user.getUid());
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading Image...");
        dialog.show();
        UploadTask task = ref.putFile(Uri.fromFile(imageFile));
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                avatar = taskSnapshot.getDownloadUrl().toString();
                refreshAvatar();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        });
        task.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                dialog.dismiss();
            }
        });
    }
}
