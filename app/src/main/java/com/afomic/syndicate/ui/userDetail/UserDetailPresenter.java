package com.afomic.syndicate.ui.userDetail;

import android.content.Intent;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.ChatDataSource;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.SingleItemDataSourceCallback;
import com.afomic.syndicate.data.UserDataSource;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.User;

import javax.inject.Inject;

public class UserDetailPresenter implements BasePresenter<UserDetailView> {
    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    ChatDataSource mDataSource;
    private UserDetailView mUserDetailView;
    private User mCurrentUser;
    private boolean myAccount=false;
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
    public void handleIntent(Intent intent){
        mCurrentUser=intent.getParcelableExtra(Constants.EXTRA_USER);
        myAccount=intent.getBooleanExtra(Constants.EXTRA_MY_ACCOUNT,false);
        mUserDetailView.showProfileFragment(mCurrentUser.getId(),myAccount);
    }
}
