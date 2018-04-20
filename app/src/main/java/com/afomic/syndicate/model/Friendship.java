package com.afomic.syndicate.model;

public class Friendship {
    private String id;
    private String userID;

    public Friendship(){

    }
    public Friendship(String userID){
        this.userID=userID;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
