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
        if(myAccount){
            mUserDetailView.showEditButtons();
            mUserDetailView.showSetAccountButton();
            mUserDetailView.hideChatButton();
            if(mCurrentUser.getId().equals(mPreferenceManager.getUserId())){
                mUserDetailView.enableSetAccountButton(false);
            }
        }else{
            mUserDetailView.hideEditButtons();
            mUserDetailView.hideSetAccountButton();
        }
        mUserDetailView.setStatus(mCurrentUser.getStatus());
        mUserDetailView.setUserId(mCurrentUser.getId());
        mUserDetailView.setUsername(mCurrentUser.getUsername());
    }
    public void setUserAccount(){
        mUserDetailView.enableSetAccountButton(false);
        mPreferenceManager.setUserId(mCurrentUser.getId());
        mUserDetailView.showMessage("Account Successfully Switched");
    }
    public void startChat(){
        mUserDetailView.showProgressBar();
        mUserDetailView.enableControls(false);
        mDataSource.startChat(mPreferenceManager.getUserId(),
                mCurrentUser.getId(), new SingleItemDataSourceCallback<Chat>() {
                    @Override
                    public void onSuccess(Chat response) {
                        mUserDetailView.hideProgressBar();
                        mUserDetailView.showMessageView(response);
                    }

                    @Override
                    public void onFailure(String message) {
                        mUserDetailView.showMessage(message);
                    }

                    @Override
                    public void hasChildren(boolean hasChild) {

                    }
                });
    }
}
