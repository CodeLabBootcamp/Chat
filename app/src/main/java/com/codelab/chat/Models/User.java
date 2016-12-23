package com.codelab.chat.Models;

import android.content.Context;
import android.content.res.Resources;

import com.codelab.chat.R;

/**
 * Created by SniperDW on 12/23/2016.
 */

public class User {

    private String id;
    private String name;
    private String email;
    private String address;
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
            case 1:
                return r.getString(R.string.male);
            case 2:
                return r.getString(R.string.female);
            case 3:
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
            this.gender = 1;
        } else if (gender.equals(r.getString(R.string.female))) {
            this.gender = 2;
        } else if (gender.equals(r.getString(R.string.other))) {
            this.gender = 3;
        } else {
            this.gender = -1;
        }
    }

}
