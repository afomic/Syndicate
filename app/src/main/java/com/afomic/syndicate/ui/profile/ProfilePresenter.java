package com.afomic.syndicate.ui.profile;

import android.content.Intent;
import android.view.MenuItem;

import com.afomic.syndicate.R;
import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.SingleItemDataSourceCallback;
import com.afomic.syndicate.data.UserDataSource;
import com.afomic.syndicate.model.User;

import javax.inject.Inject;

public class ProfilePresenter implements BasePresenter<ProfileView> {
    @Inject
    UserDataSource mDataSource;
    @Inject
    PreferenceManager mPreferenceManager;

    private User mUser;
    private ProfileView mProfileView;
    private String userId;

    @Inject
    public ProfilePresenter(){

    }
    @Override
    public void takeView(ProfileView view) {
        mProfileView=view;
        mProfileView.setUpView();
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
                    mDataSource.setHasMultipleAccount(mPreferenceManager.getUniqueId());
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
}
