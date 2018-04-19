package com.afomic.syndicate;

import android.app.Application;

import com.afomic.syndicate.di.AppComponent;
import com.afomic.syndicate.di.AppModule;
import com.afomic.syndicate.di.DaggerAppComponent;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

public class Syndicate extends Application {
    AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent= DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        FirebaseDatabase.getInstance()
                .setLogLevel(Logger.Level.DEBUG);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    public AppComponent getComponent(){
        return appComponent;
    }
}
