package com.afomic.syndicate.ui.main;


import android.view.MenuItem;

import com.afomic.syndicate.R;
import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.PreferenceManager;

import javax.inject.Inject;

public class MainPresenter implements BasePresenter<MainView> {
    private MainView mMainView;

    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    public MainPresenter(){

    }
    @Override
    public void takeView(MainView view) {
        mMainView=view;
        mMainView.setUpView();
    }

    @Override
    public void dropView() {

    }
    public void navigationItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menu_chats:
                mMainView.showChatListView();
                break;
            case R.id.menu_settings:
                mMainView.showSettingsView();
                break;
            case R.id.menu_profile:
                if(mPreferenceManager.hasMultipleAccount()){
                    mMainView.showMultipleAccountView();
                }else {
                    mMainView.showProfileView(mPreferenceManager.getUserId());
                }
                break;
        }
    }
}
