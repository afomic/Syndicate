package com.afomic.syndicate.ui.welcome;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.PreferenceManager;

import javax.inject.Inject;

public class WelcomePresenter implements BasePresenter<WelcomeView> {
    @Inject
    PreferenceManager mPreferenceManager;
    private WelcomeView mWelcomeView;
    @Inject
    public WelcomePresenter(){
    }
    @Override
    public void takeView(WelcomeView view) {
        mWelcomeView=view;
    }

    @Override
    public void dropView() {

    }
    public void showNextActivity(){
        if(mPreferenceManager.isLoggedIn()){
            mWelcomeView.showHomeView();
        }else {
            mWelcomeView.showLoginView();
        }
    }
}
