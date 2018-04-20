package com.afomic.syndicate.ui.userDetail;

import android.content.Intent;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.model.User;

import javax.inject.Inject;

public class UserDetailPresenter implements BasePresenter<UserDetailView> {
    @Inject
    PreferenceManager mPreferenceManager;
    private UserDetailView mUserDetailView;
    private User mCurrentUser;
    @Inject
    public UserDetailPresenter(){

    }
    @Override
    public void takeView(UserDetailView view) {
        mUserDetailView=view;
    }

    @Override
    public void dropView() {

    }
    public void handleIntent(Intent intent){
        mCurrentUser=intent.getParcelableExtra(Constants.EXTRA_USER);
        mUserDetailView.showUserDetails(mCurrentUser);
    }
    public void startChat(){

    }
    public void addFriend(){

    }
}
