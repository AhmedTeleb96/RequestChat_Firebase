package com.ahmedteleb.requestchat.RecyclerViewReceiver;

public class ReceiverObject
{
    private  String email;
    private String uid;
    private boolean receive;

    public ReceiverObject(String email, String uid, boolean receive)
    {
        this.email = email;
        this.uid = uid;
        this.receive = receive;
    }

    public boolean isReceive() {
        return receive;
    }

    public void setReceive(boolean receive) {
        this.receive = receive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
