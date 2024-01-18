package com.example.quickchat;

public class msgModelclass {
    String messsage;
    String senderid;
    long timeStamp;

    public msgModelclass() {

    }

    public msgModelclass(String messsage, String senderid, long timeStamp) {
        this.messsage = messsage;
        this.senderid = senderid;
        this.timeStamp = timeStamp;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
