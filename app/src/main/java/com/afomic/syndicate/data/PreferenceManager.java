package com.afomic.syndicate.data;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferenceManager {
    private SharedPreferences mSharedPreferences;

    private static final String PREFERENCE_FILE_NAME="com.example.afomic.syndicate";
    private static final String PREF_USER_IS_LOGGED_IN="has_account";
    private static final String PREF_USER_ID="user_id";
    private static final String PREF_CURRENT_CHAT_ID="chat_id";
    private static  final String PREF_UNIQUE_ID="chat_id";
    private static final String PREF_HAS_MULTIPLE_ACCOUNT="has_multiple_account";

    @Inject
    public PreferenceManager(Context context){
        mSharedPreferences=context.getSharedPreferences(PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
    }
    public void setLoggedIn(boolean loggedIn){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putBoolean(PREF_USER_IS_LOGGED_IN,loggedIn);
        mEditor.apply();
    }

    public boolean isLoggedIn(){
        return mSharedPreferences.getBoolean(PREF_USER_IS_LOGGED_IN,false);
    }
    public void setUniqueId(String uniqueId){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(PREF_UNIQUE_ID,uniqueId);
        mEditor.apply();
    }
    public String getUniqueId(){
        return mSharedPreferences.getString(PREF_UNIQUE_ID,"id");
    }

    public void setHasMultipleAccount(boolean hasMultipleAccount){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putBoolean(PREF_HAS_MULTIPLE_ACCOUNT,hasMultipleAccount);
        mEditor.apply();

    }
    public boolean hasMultipleAccount(){
        return mSharedPreferences.getBoolean(PREF_HAS_MULTIPLE_ACCOUNT,false);
    }

    public void setUserId(String userId){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(PREF_USER_ID,userId);
        mEditor.apply();
    }

    public String getUserId(){
        return mSharedPreferences.getString(PREF_USER_ID,"no_user");
    }

    public void setCurrentChatId(String chatId){
        SharedPreferences.Editor mEditor=mSharedPreferences.edit();
        mEditor.putString(PREF_CURRENT_CHAT_ID,chatId);
        mEditor.apply();
    }
    public String getCurrentChatId(){
        return mSharedPreferences.getString(PREF_CURRENT_CHAT_ID,"chat_id");
    }
}
