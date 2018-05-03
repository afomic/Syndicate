package com.afomic.syndicate;

import android.app.Application;
import android.content.Intent;

import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.services.FirebaseChatListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

public class Syndicate extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DependencyInjector.initialize(this);
        FirebaseDatabase.getInstance()
                .setLogLevel(Logger.Level.DEBUG);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Intent intent=new Intent(getApplicationContext(),FirebaseChatListener.class);
        startService(intent);
    }
}
