package com.afomic.syndicate.ui.signUp;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.AuthManager;

import javax.inject.Inject;

public class SignUpPresenter implements BasePresenter<SignUpView> {
    @Inject
    AuthManager mAuthManager;

    private SignUpView mSignUpView;
    @Inject
    public SignUpPresenter(){

    }
    @Override
    public void takeView(SignUpView view) {
        mSignUpView=view;
    }

    @Override
    public void dropView() {

    }
    public void createUser(String email,String password){

    }
    public void onBackPressed(){

    }

}
