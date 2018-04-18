package com.afomic.syndicate.ui.signUp;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.AuthManager;
import com.afomic.syndicate.model.User;

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
    public void createUser(String email,String password,
                           String firstName,String lastName){
        mSignUpView.resetError();
        if(isValidEmail(email)
                &&isValidFirstName(firstName)
                &&isValidPassword(password)
                &&isValidLastName(lastName)){
            User user=new User();
            user.setTimeCreated(System.currentTimeMillis());
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setStatus("New User");
            mSignUpView.showProgressBar();
            mAuthManager.createUser(user, email, password, new AuthManager.AuthManagerCallback() {
                @Override
                public void onSuccess() {
                    mSignUpView.hideProgressBar();
                    mSignUpView.showMainView();
                }

                @Override
                public void onFailure(String message) {
                    mSignUpView.hideProgressBar();
                    mSignUpView.showMessage(message);
                }
            });
        }

    }
    private boolean isEmpty(String value){
        return value==null||value.equals("");
    }
    private boolean isValidFirstName(String firstName){
        if(isEmpty(firstName)){
            mSignUpView.showFirstNameError("First Name Cannot be Empty");
            return false;
        }
        return true;
    }
    private boolean isValidLastName(String lastName){
        if(isEmpty(lastName)){
            mSignUpView.showLastNameError("First Name Cannot be Empty");
            return false;
        }
        return true;
    }
    private boolean isValidEmail(String email){
        if(isEmpty(email)){
            mSignUpView.showEmailError("Email Cannot be Empty");
            return false;
        }
        return true;
    }
    private boolean isValidPassword(String password){
        if(isEmpty(password)){
            mSignUpView.showPasswordError("Password Cannot be Empty");
            return false;
        }
        if(password.length()<6){
            mSignUpView.showPasswordError("Password is too Short");
            return false;
        }
        return true;
    }

}
