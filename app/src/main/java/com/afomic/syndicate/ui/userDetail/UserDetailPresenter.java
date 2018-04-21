package com.afomic.syndicate.ui.userDetail;

import android.content.Intent;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.data.DataSource;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.SingleItemDataSourceCallback;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.User;

import javax.inject.Inject;

public class UserDetailPresenter implements BasePresenter<UserDetailView> {
    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    DataSource mDataSource;
    private UserDetailView mUserDetailView;
    private User mCurrentUser;
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
        mUserDetailView.showUserDetails(mCurrentUser);
    }
    public void startChat(){
        mUserDetailView.showProgressBar();
        mDataSource.startChat(mPreferenceManager.getUserId(),
                mCurrentUser.getId(), new SingleItemDataSourceCallback<Chat>() {
                    @Override
                    public void onSuccess(Chat response) {

                    }

                    @Override
                    public void onFailure(String message) {

                    }

                    @Override
                    public void hasChildren(boolean hasChild) {

                    }
                });
    }
    public void addFriend(){
        mDataSource.addFriend(mPreferenceManager.getUserId(),
                mCurrentUser.getId(), new SingleItemDataSourceCallback<User>() {
                    @Override
                    public void onSuccess(User response) {

                    }

                    @Override
                    public void onFailure(String message) {

                    }

                    @Override
                    public void hasChildren(boolean hasChild) {

                    }
                });
    }
}
