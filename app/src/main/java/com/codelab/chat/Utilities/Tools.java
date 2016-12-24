package com.codelab.chat.Utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by SniperDW on 12/24/2016.
 */

public class Tools {

    public static String formatTimestamp(long timestamp) {
        Date date = new Date();
        date.setTime(timestamp);
        DateFormat df = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return df.format(date);
    }
}
