package com.afomic.syndicate;

import android.app.Application;

import com.afomic.syndicate.di.DependencyInjector;
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
    }
}
