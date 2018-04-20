package com.afomic.syndicate.ui.userDetail;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.PreferenceManager;

import javax.inject.Inject;

public class UserDetailPresenter implements BasePresenter<UserDetailView> {
    @Inject
    PreferenceManager mPreferenceManager;
    private UserDetailView mUserDetailView;
    @Inject
    public UserDetailPresenter(){

    }
    @Override
    public void takeView(UserDetailView view) {
        mUserDetailView=view;
        mUserDetailView.setUpView();
    }

    @Override
    public void dropView() {

    }
}
