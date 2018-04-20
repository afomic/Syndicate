package com.afomic.syndicate.ui.userDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.afomic.syndicate.R;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.di.DependencyInjector;

import javax.inject.Inject;

public class UserDetailActivity extends BaseActivity implements UserDetailView{
    @Inject
    UserDetailPresenter mUserDetailPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        DependencyInjector
                .applicationComponent()
                .inject(this);
        mUserDetailPresenter.takeView(this);
    }

    @Override
    public void setUpView() {

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
