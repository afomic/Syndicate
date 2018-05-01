package com.afomic.syndicate.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
    private String id;
    private String username;
    private String status;
    private String uniqueId;
    private long timeCreated;
    private String pictureUrl;
    public User(){

    }

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        status = in.readString();
        timeCreated = in.readLong();
        pictureUrl = in.readString();
        uniqueId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(status);
        dest.writeLong(timeCreated);
        dest.writeString(pictureUrl);
        dest.writeString(uniqueId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String firstName) {
        this.username = firstName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
