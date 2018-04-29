package com.afomic.syndicate.ui.profile;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.SingleItemDataSourceCallback;
import com.afomic.syndicate.data.UserDataSource;
import com.afomic.syndicate.model.User;

import javax.inject.Inject;

public class ProfilePresenter implements BasePresenter<ProfileView> {
    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    UserDataSource mDataSource;
    private ProfileView mProfileView;
    @Inject
    public ProfilePresenter(){

    }
    @Override
    public void takeView(ProfileView view) {
        mProfileView=view;
        mProfileView.setUpView();
    }
    public void loadData(){
        mDataSource.getUser(mPreferenceManager.getUserId(), new SingleItemDataSourceCallback<User>() {
            @Override
            public void onSuccess(User response) {
                mProfileView.showProfile(response);
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


    @Override
    public void dropView() {

    }
}
