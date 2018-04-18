package com.afomic.syndicate.ui.login;

import com.afomic.syndicate.base.BaseView;

public interface LoginView extends BaseView {
    void showMainView();
    void showSignUpView();
    void showEmailError(String error);
    void showPasswordError(String error);
    void resetError();

}
