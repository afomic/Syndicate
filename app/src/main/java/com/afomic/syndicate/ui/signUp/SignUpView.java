package com.afomic.syndicate.ui.signUp;

import com.afomic.syndicate.base.BaseView;

public interface SignUpView  extends BaseView{
    void showMainView();
    void showLoginView();
    void resetError();
    void showEmailError(String error);
    void showPasswordError(String error);
    void showFirstNameError(String error);
    void showLastNameError(String error);

}
