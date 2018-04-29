package com.afomic.syndicate.ui.welcome;

import android.util.Log;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.AuthManager;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.UserDataSource;

import javax.inject.Inject;

public class WelcomePresenter implements BasePresenter<WelcomeView> {
    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    AuthManager mAuthManager;
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
        if(!mPreferenceManager.isLoggedIn()){
            mPreferenceManager.setUniqueId(UserDataSource.getRandomString(16));
            mWelcomeView.showLoadingLayout();
            mAuthManager.signUp(new AuthManager.AuthManagerCallback() {
                @Override
                public void onSuccess() {
                    mWelcomeView.showHomeView();
                }

                @Override
                public void onFailure(String message) {
                    mWelcomeView.showMessage(message);
                }
            });
        }else {
            mWelcomeView.showHomeView();
        }

    }
}
