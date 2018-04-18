package com.afomic.syndicate.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.afomic.syndicate.Syndicate;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.ui.home.MainActivity;
import com.afomic.syndicate.ui.login.LoginActivity;

import javax.inject.Inject;

public class WelcomeActivity extends BaseActivity implements WelcomeView{
    @Inject
    WelcomePresenter welcomePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Syndicate)getApplication())
                .getComponent()
                .inject(this);
        welcomePresenter.takeView(this);
        welcomePresenter.showNextActivity();
    }

    @Override
    public void showHomeView() {
        showActivityAndFinish(MainActivity.class);
    }

    @Override
    public void showLoginView() {
        showActivityAndFinish(LoginActivity.class);
    }


    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
