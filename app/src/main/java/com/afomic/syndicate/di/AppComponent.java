package com.afomic.syndicate.di;



import com.afomic.syndicate.ui.login.LoginActivity;
import com.afomic.syndicate.ui.login.LoginPresenter;
import com.afomic.syndicate.ui.signUp.SignUpActivity;
import com.afomic.syndicate.ui.welcome.WelcomeActivity;
import com.afomic.syndicate.ui.welcome.WelcomePresenter;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    LoginPresenter getLoginPresenter();
    WelcomePresenter getWelcomePresenter();
    void inject(WelcomeActivity activity);
    void inject(LoginActivity activity);
    void inject(SignUpActivity activity);
}
