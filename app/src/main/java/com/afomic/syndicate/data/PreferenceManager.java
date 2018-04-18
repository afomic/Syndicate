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
}
