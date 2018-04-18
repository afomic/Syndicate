package com.afomic.syndicate.ui.login;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.AuthManager;
import com.afomic.syndicate.model.User;

import javax.inject.Inject;

public class LoginPresenter implements BasePresenter<LoginView> {
    private LoginView mLoginView;
    @Inject
    AuthManager mAuthManager;
    @Inject
    public LoginPresenter(){

    }
    @Override
    public void takeView(LoginView view) {
        mLoginView=view;
    }
    public void signUpButtonClicked(){
        mLoginView.showSignUpView();
    }

    @Override
    public void dropView() {

    }
    public void loginUser(String email,String password ){
        if(isValidEmail(email)&&isValidPassword(password)){
           mLoginView.showProgressBar();
           mAuthManager.login(email, password, new AuthManager.AuthManagerCallback() {
               @Override
               public void onSuccess() {
                   mLoginView.hideProgressBar();
                   mLoginView.showMainView();
               }

               @Override
               public void onFailure(String message) {
                   mLoginView.hideProgressBar();
                   mLoginView.showMessage(message);
               }
           });
        }

    }
    private boolean isEmpty(String value){
        return value==null||value.equals("");
    }
    private boolean isValidEmail(String email){
        if(isEmpty(email)){
            mLoginView.showEmailError("Email Cannot be Empty");
            return false;
        }
        return true;
    }
    private boolean isValidPassword(String password){
        if(isEmpty(password)){
            mLoginView.showPasswordError("Password Cannot be Empty");
            return false;
        }
        if(password.length()<6){
            mLoginView.showPasswordError("Password is too Short");
            return false;
        }
        return true;
    }
}
