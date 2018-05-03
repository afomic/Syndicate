package com.afomic.syndicate.ui.main;


import android.os.Bundle;
import android.util.Log;
import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.ListDataSourceCallback;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.SingleItemDataSourceCallback;
import com.afomic.syndicate.data.UserDataSource;
import com.afomic.syndicate.model.NavItem;
import com.afomic.syndicate.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainPresenter implements BasePresenter<MainView> {
    private MainView mMainView;
    private ArrayList<NavItem> mNavItems;
    NavItem lastItemSelected;
    private int lastNavItemSelectedPosition;
    private static final String BUNDLE_SELECTED_NAV_ITEM_POSITION="nav_item_postion";
    private static final String BUNDLE_NAV_LIST="nav_list";


    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    UserDataSource mDataSource;
    @Inject
    public MainPresenter(){
        mNavItems=new ArrayList<>();
    }
    @Override
    public void takeView(MainView view) {
        mMainView=view;
        mMainView.setUpView();
    }

    @Override
    public void dropView() {

    }
    public void displayNavigation(){
        mDataSource.getUserAccounts(mPreferenceManager.getUniqueId(), new ListDataSourceCallback<User>() {
            @Override
            public void onSuccess(List<User> response) {
                showMenu(response);
            }

            @Override
            public void onFailure(String message) {
                mMainView.showMessage(message);
            }

            @Override
            public void hasChildren(boolean hasChild) {
                mMainView.hideProgressBar();
            }
        });
    }
    public void showMenu(List<User> users){
        mNavItems.clear();
        NavItem chatNavItem=new NavItem("Chats","",NavItem.TYPE_CHAT);
        mNavItems.add(chatNavItem);
        for(User user: users){
            mNavItems.add(new NavItem(user));
        }
        mNavItems.add(new NavItem("Settings","",NavItem.TYPE_SETTINGS));
        mMainView.showNavigation(mNavItems);
        if(lastItemSelected==null){
            chatNavItem.setSelected(true);
            lastItemSelected=chatNavItem;
        }
        navigationItemSelected(lastNavItemSelectedPosition);
    }
    public void navigationItemSelected(int position){
        NavItem navItem=mNavItems.get(position);
        lastItemSelected.setSelected(false);
        navItem.setSelected(true);
        lastNavItemSelectedPosition=position;
        switch (navItem.getType()){
            case NavItem.TYPE_CHAT:
                mMainView.showChatListView();
                break;
            case NavItem.TYPE_SETTINGS:
                mMainView.showSettingsView();
                break;
            case NavItem.TYPE_USER:
                mMainView.showProfileView(navItem.getUserId());
                break;
        }
        lastItemSelected=navItem;
        mMainView.notifyDataChange();
    }
    public void addNewAccount(){
        mMainView.showProgressBar();
        Log.e("unique","unique id:"+mPreferenceManager.getUniqueId());
        mDataSource.saveUser(mPreferenceManager.getUniqueId(), new SingleItemDataSourceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                mMainView.hideProgressBar();
                if(response){
                    mMainView.showMessage("New Account created");
                }
            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void hasChildren(boolean hasChild) {

            }
        });
    }
    public void writeState(Bundle outState){
        outState.putInt(BUNDLE_SELECTED_NAV_ITEM_POSITION,lastNavItemSelectedPosition);
    }
    public void restoreState(Bundle savedInstance){
        lastNavItemSelectedPosition=savedInstance.getInt(BUNDLE_SELECTED_NAV_ITEM_POSITION);
    }
}
