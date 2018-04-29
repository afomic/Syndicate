package com.afomic.syndicate.ui.updateProfile;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.UserDataSource;

import javax.inject.Inject;

public class UpdateProfilePresenter implements BasePresenter<UpdateProfileView> {
    @Inject
    UserDataSource mUserDataSource;
    @Inject
    public UpdateProfilePresenter(){

    }

    @Override
    public void takeView(UpdateProfileView view) {

    }

    @Override
    public void dropView() {

    }
}
