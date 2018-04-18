package com.afomic.syndicate;

import android.app.Application;

import com.afomic.syndicate.di.AppComponent;
import com.afomic.syndicate.di.AppModule;
import com.afomic.syndicate.di.DaggerAppComponent;

public class Syndicate extends Application {
    AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent= DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
    public AppComponent getComponent(){
        return appComponent;
    }
}
