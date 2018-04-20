package com.afomic.syndicate.ui.profile;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.DataSource;
import com.afomic.syndicate.data.PreferenceManager;

import javax.inject.Inject;

public class ProfilePresenter implements BasePresenter<ProfileView> {
    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    DataSource mDataSource;
    private ProfileView mProfileView;
    @Inject
    public ProfilePresenter(){

    }
    @Override
    public void takeView(ProfileView view) {
        mProfileView=view;
        mProfileView.setUpView();
    }


    @Override
    public void dropView() {

    }
}
