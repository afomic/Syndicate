package com.afomic.syndicate.di;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;


import com.afomic.syndicate.ui.login.LoginActivity;
import com.afomic.syndicate.ui.welcome.WelcomeActivity;
import com.afomic.syndicate.ui.welcome.WelcomePresenter;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context getContext();
    WelcomePresenter getWelcomePresenter();
    void inject(WelcomeActivity activity);
    void inject(LoginActivity activity);
}
