package com.afomic.syndicate.di;

import com.afomic.syndicate.Syndicate;

public class DependencyInjector {

    private static AppComponent applicationComponent;

    public static void initialize(Syndicate application) {
        applicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }

    public static AppComponent applicationComponent() {
        return applicationComponent;
    }

    private DependencyInjector(){}
}