package com.afomic.syndicate.ui.login;

import com.afomic.syndicate.base.BasePresenter;

public class LoginPresenter implements BasePresenter<LoginView> {
    private LoginView mLoginView;
    @Override
    public void takeView(LoginView view) {
        mLoginView=view;
    }

    @Override
    public void dropView() {

    }
    public void loginUser(String email,String password ){
        if(isValid(email)&&isValid(password)){

        }else {
            mLoginView.showMessage("Invalid Email or password");
        }

    }
    private boolean isValid(String value){
        return value!=null&&value.equals("");
    }
}
