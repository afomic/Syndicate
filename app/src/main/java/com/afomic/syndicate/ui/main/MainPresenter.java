package com.afomic.syndicate.ui.main;


import android.view.MenuItem;

import com.afomic.syndicate.R;
import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.PreferenceManager;

import javax.inject.Inject;

public class MainPresenter implements BasePresenter<MainView> {
    @Inject
    PreferenceManager mPreferenceManager;

    private MainView mMainView;
    @Inject
    public MainPresenter(){

    }
    @Override
    public void takeView(MainView view) {
        mMainView=view;
    }

    @Override
    public void dropView() {

    }
    public void navigationItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menu_chats:
                mMainView.showChatListView();
                break;
            case R.id.menu_friends:
                mMainView.showFriendListView();
                break;
            case R.id.menu_profile:
                mMainView.showProfileView();
                break;
        }
    }
}
