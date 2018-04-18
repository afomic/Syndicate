package com.afomic.syndicate.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.afomic.syndicate.R;
import com.afomic.syndicate.Syndicate;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.ui.home.MainActivity;
import com.afomic.syndicate.ui.signUp.SignUpActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {
    @BindView(R.id.edt_email)
    EditText emailEditText;
    @BindView(R.id.edt_password)
    EditText passwordEditText;
    @BindView(R.id.til_email)
    TextInputLayout emailTextInputLayout;
    @BindView(R.id.til_password)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.progress_bar_layout)
    RelativeLayout progressBarLayout;

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
        String email=getText(emailEditText);
        String password=getText(passwordEditText);
        mLoginPresenter.loginUser(email,password);
    }
    @OnClick(R.id.btn_sign_up)
    public void createUser(){
        mLoginPresenter.signUpButtonClicked();
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showProgressBar() {
        progressBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void showMainView() {
        showActivityAndFinish(MainActivity.class);
    }

    @Override
    public void showSignUpView() {
        showActivity(SignUpActivity.class);
    }

    @Override
    public void showEmailError(String error) {
        emailTextInputLayout.setError(error);
    }

    @Override
    public void showPasswordError(String error) {
        passwordTextInputLayout.setError(error);
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.dropView();
        super.onDestroy();
    }
}
