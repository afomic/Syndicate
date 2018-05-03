package com.afomic.syndicate.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NavItem implements Parcelable{

    public static final int TYPE_USER=0;
    public static final int TYPE_CHAT=1;
    public static final int TYPE_SETTINGS=2;


    private String userId;
    private int type;
    private String title;
    private boolean selected=false;
    public NavItem(String title,String userID,int type){
        this.userId=userID;
        this.type=type;
        this.title=title;
    }
    public NavItem(User user){
        title=user.getUsername();
        userId=user.getId();
        type=TYPE_USER;
    }

    protected NavItem(Parcel in) {
        userId = in.readString();
        type = in.readInt();
        title = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<NavItem> CREATOR = new Creator<NavItem>() {
        @Override
        public NavItem createFromParcel(Parcel in) {
            return new NavItem(in);
        }

        @Override
        public NavItem[] newArray(int size) {
            return new NavItem[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeByte((byte) (selected ? 1 : 0));
    }
}
