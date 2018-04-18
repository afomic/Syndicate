package com.afomic.syndicate.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Application application;
    public AppModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    public Context context(){ return application; }
}
