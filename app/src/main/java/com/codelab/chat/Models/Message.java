package com.codelab.chat.Models;

/**
 * Created by SniperDW on 12/23/2016.
 */

public class Message {

    private String senderId;
    private String content;
    private long timestamp;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
