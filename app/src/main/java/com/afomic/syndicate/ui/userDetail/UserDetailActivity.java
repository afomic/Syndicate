package com.afomic.syndicate.ui.userDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.afomic.syndicate.R;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailActivity extends BaseActivity implements UserDetailView{
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_user_name)
    TextView usernameTextView;
    @BindView(R.id.tv_user_status)
    TextView statusTextView;
    @BindView(R.id.btn_chat)
    Button chatButton;
    @BindView(R.id.btn_add_friend)
    Button addFriendButton;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Inject
    UserDetailPresenter mUserDetailPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        DependencyInjector
                .applicationComponent()
                .inject(this);
        mUserDetailPresenter.takeView(this);
        mUserDetailPresenter.handleIntent(getIntent());
    }

    @Override
    public void setUpView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showProgressBar() {
        if(!mProgressBar.isShown()){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        if(mProgressBar.isShown()){
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showUserDetails(User user) {
        statusTextView.setText(user.getStatus());
        usernameTextView.setText(user.getFirstName());
    }

    @OnClick(R.id.btn_add_friend)
    public void addFriend(){
        mUserDetailPresenter.addFriend();
    }
    @OnClick(R.id.btn_chat)
    public void createChat(){
        mUserDetailPresenter.startChat();
    }

    @Override
    public void showMessageView(Chat chat) {

    }

    @Override
    public void enableControls(boolean enable) {
        addFriendButton.setEnabled(enable);
        chatButton.setEnabled(enable);
    }

    @Override
    public void disableAddFriend() {
        addFriendButton.setEnabled(false);
    }
}
