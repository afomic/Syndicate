package com.afomic.syndicate.ui.profile;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.afomic.syndicate.R;
import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.ChatDataSource;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.SingleItemDataSourceCallback;
import com.afomic.syndicate.data.UserDataSource;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.User;

import javax.inject.Inject;

public class ProfilePresenter implements BasePresenter<ProfileView> {
    @Inject
    UserDataSource mDataSource;
    @Inject
    ChatDataSource mChatDataSource;
    @Inject
    PreferenceManager mPreferenceManager;

    private User mUser;
    private ProfileView mProfileView;
    private String userId;
    private boolean myAccount;

    @Inject
    public ProfilePresenter(){

    }
    @Override
    public void takeView(ProfileView view) {
        mProfileView=view;
        mProfileView.setUpView();
        displayView();
    }
    public void setIsMyAccount(boolean myAccount){
        this.myAccount=myAccount;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }
    public void loadData(){
        mDataSource.getUser(userId, new SingleItemDataSourceCallback<User>() {
            @Override
            public void onSuccess(User response) {
                mUser=response;
                mProfileView.showStatus(response.getStatus());
                mProfileView.showUsername(response.getUsername());
                mProfileView.showId(response.getId());
            }

            @Override
            public void onFailure(String message) {
                mProfileView.showMessage(message);
            }

            @Override
            public void hasChildren(boolean hasChild) {

            }
        });
    }
    public void startChat(){
        mProfileView.showProgressBar();
        mChatDataSource.startChat(mPreferenceManager.getUserId(),
                userId, new SingleItemDataSourceCallback<Chat>() {
                    @Override
                    public void onSuccess(Chat response) {
                        mProfileView.hideProgressBar();
                        mProfileView.showMessageView(response);
                    }

                    @Override
                    public void onFailure(String message) {
                        mProfileView.showMessage(message);
                    }

                    @Override
                    public void hasChildren(boolean hasChild) {

                    }
                });
    }
    public void handleIntent(Intent intent){
        String newValue=intent.getStringExtra(Constants.EXTRA_NEW_VALUE);
        String key=intent.getStringExtra(Constants.EXTRA_KEY);
        if(key.equals("status")){
           mProfileView.showStatus(newValue);
        }else if(key.equals("username")){
            mProfileView.showUsername(newValue);
        }
    }
    public void handleStatusEdit(){
        mProfileView.showEditDialog(userId,"status",mUser.getStatus());
    }
    public void handleDisplayNameEdit(){
        mProfileView.showEditDialog(userId,"username",mUser.getUsername());
    }
    @Override
    public void dropView() {

    }
    public void handleMenuSelected(MenuItem menuItem){
        mProfileView.showProgressBar();
        if(menuItem.getItemId()== R.id.menu_add_account){
            mDataSource.saveUser(mPreferenceManager.getUniqueId(), new SingleItemDataSourceCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean response) {
                    mProfileView.hideProgressBar();
                    mProfileView.showMessage("New Account created");
                }

                @Override
                public void onFailure(String message) {
                    mProfileView.hideProgressBar();
                    mProfileView.showMessage("operation failed");
                }

                @Override
                public void hasChildren(boolean hasChild) {

                }
            });
        }

    }
    public void displayView(){
        if(myAccount){
            mProfileView.showEditButtons();
            mProfileView.showSetAccountButton();
            mProfileView.hideChatButton();
            if(userId.equals(mPreferenceManager.getUserId())){
                mProfileView.enableSetAccountButton(false);
            }
        }else{
            mProfileView.hideEditButtons();
            mProfileView.hideSetAccountButton();
        }
    }
    public void setUserAccount(){
        mProfileView.enableSetAccountButton(false);
        mPreferenceManager.setUserId(userId);
        mProfileView.startNotificationService();
        mProfileView.showMessage("Account Successfully Switched");
    }
    public void inflateMenu(Menu menu, MenuInflater inflater){
        if(myAccount&&!mPreferenceManager.hasMultipleAccount()){
            inflater.inflate(R.menu.menu_account,menu);
        }
    }
}
