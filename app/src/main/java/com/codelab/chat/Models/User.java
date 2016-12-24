package com.codelab.chat.Models;

import android.content.Context;
import android.content.res.Resources;

import com.codelab.chat.R;
import com.codelab.chat.Utilities.Gender;

/**
 * Created by SniperDW on 12/23/2016.
 */

public class User {

    private String id;
    private String name;
    private String email;
    private String address;
    private String avatar;
    private int age;
    private int gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public String getGender(Context context) {
        Resources r = context.getResources();
        switch (gender) {
            case Gender.MALE:
                return r.getString(R.string.male);
            case Gender.FEMALE:
                return r.getString(R.string.female);
            case Gender.OTHER:
                return r.getString(R.string.other);
            default:
                return "Unknown";
        }
    }


    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setGender(Context context, String gender) {

        Resources r = context.getResources();

        if (gender.equals(r.getString(R.string.male))) {
            this.gender = Gender.MALE;
        } else if (gender.equals(r.getString(R.string.female))) {
            this.gender = Gender.FEMALE;
        } else if (gender.equals(r.getString(R.string.other))) {
            this.gender = Gender.OTHER;
        } else {
            this.gender = -1;
        }
    }

    public String getAvatar() {
        if (avatar == null || avatar.isEmpty())
            avatar = "http://www.google.com";
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
