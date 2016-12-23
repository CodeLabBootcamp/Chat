package com.codelab.chat.Models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by SniperDW on 12/23/2016.
 */

public class FBHelper {

    public static DatabaseReference getReference(String ref) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(ref);
    }
}
