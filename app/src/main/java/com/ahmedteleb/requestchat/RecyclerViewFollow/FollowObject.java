package com.ahmedteleb.requestchat.RecyclerViewFollow;

public class FollowObject
{
    private  String email;
    private String uid;

    public FollowObject(String email, String uid)
    {
        this.email = email;
        this.uid = uid;
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
