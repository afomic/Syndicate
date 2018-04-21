package com.afomic.syndicate.ui.signUp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.afomic.syndicate.R;
import com.afomic.syndicate.Syndicate;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.ui.main.MainActivity;
import com.afomic.syndicate.ui.login.LoginActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements SignUpView {
    @BindView(R.id.progress_bar_layout)
    RelativeLayout progressBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_password)
    EditText passwordEditText;
    @BindView(R.id.edt_email)
    EditText emailEditText;
    @BindView(R.id.edt_first_name)
    EditText firstNameEditText;
    @BindView(R.id.edt_last_name)
    EditText lastNameEditText;

    @BindView(R.id.til_password)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.til_email)
    TextInputLayout emailTextInputLayout;
    @BindView(R.id.til_first_name)
    TextInputLayout firstNameTextInputLayout;
    @BindView(R.id.til_last_name)
    TextInputLayout lastNameTextInputLayout;

    @Inject
    SignUpPresenter mSignUpPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        DependencyInjector
                .applicationComponent()
                .inject(this);
        mSignUpPresenter.takeView(this);
    }
    @Override
    public void setUpView(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void showLoginView() {
        showActivityAndFinish(LoginActivity.class);
    }

    @Override
    public void resetError() {
        lastNameTextInputLayout.setErrorEnabled(false);
        firstNameTextInputLayout.setErrorEnabled(false);
        emailTextInputLayout.setErrorEnabled(false);
        passwordTextInputLayout.setErrorEnabled(false);
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
    public void showFirstNameError(String error) {
        firstNameTextInputLayout.setError(error);
    }

    @Override
    public void showLastNameError(String error) {
        lastNameTextInputLayout.setError(error);
    }
    @OnClick(R.id.btn_sign_up)
    public void signUpUser(){
        String email=getText(emailEditText);
        String password=getText(passwordEditText);
        String firstName=getText(firstNameEditText);
        String lastName=getText(lastNameEditText);
        mSignUpPresenter.createUser(email,password,firstName,lastName);
    }

    @Override
    protected void onDestroy() {
        mSignUpPresenter.dropView();
        super.onDestroy();
    }
}
