package com.afomic.syndicate.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.afomic.syndicate.R;
import com.afomic.syndicate.Syndicate;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {
    @BindView(R.id.edt_email)
    EditText emailEditText;
    @BindView(R.id.edt_password)
    EditText passwordEditText;
    @BindView(R.id.til_email)
    TextInputLayout emailTextInputLayout;
    @BindView(R.id.til_password)
    TextInputLayout passwordTextInputLayout;

    @Inject
    LoginPresenter mLoginPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((Syndicate)getApplication())
                .getComponent()
                .inject(this);
        mLoginPresenter.takeView(this);
    }
    @OnClick(R.id.btn_login)
    public void loginUser(){

    }
    @OnClick(R.id.btn_sign_up)
    public void createUser(){

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

    @Override
    public void showMainView() {

    }

    @Override
    public void showSignUpView() {

    }

    @Override
    public void showEmailError() {

    }

    @Override
    public void showPasswordError() {

    }
}
