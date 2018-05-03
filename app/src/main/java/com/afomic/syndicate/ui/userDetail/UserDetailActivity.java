package com.afomic.syndicate.ui.userDetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.afomic.syndicate.R;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.User;
import com.afomic.syndicate.ui.messages.MessageActivity;
import com.afomic.syndicate.ui.profile.ProfileFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailActivity extends BaseActivity implements UserDetailView {

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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


    }

    @Override
    public void showProfileFragment(String userId, boolean myAccount) {
        ProfileFragment profileFragment=ProfileFragment.newInstance(userId,myAccount);
        getSupportFragmentManager().beginTransaction().add(R.id.profile_container, profileFragment)
                .commit();
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

}
