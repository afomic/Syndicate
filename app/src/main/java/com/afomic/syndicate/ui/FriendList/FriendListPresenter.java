package com.afomic.syndicate.ui.FriendList;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.DataSource;
import com.afomic.syndicate.data.ListDataSourceCallback;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.model.User;

import java.util.List;

import javax.inject.Inject;

public class FriendListPresenter implements BasePresenter<FriendListView> {
    @Inject
    DataSource mDataSource;
    @Inject
    PreferenceManager mPreferenceManager;
    private ListDataSourceCallback<User> mUserListDataSourceCallback;
    private FriendListView mFriendListView;
    @Inject
    public FriendListPresenter(){

    }
    @Override
    public void takeView(FriendListView view) {
        mFriendListView=view;
        mFriendListView.setUpView();
    }

    @Override
    public void dropView() {
        mUserListDataSourceCallback=null;
    }
    public void loadData(){
        mFriendListView.showProgressBar();
        mUserListDataSourceCallback=new ListDataSourceCallback<User>() {
            @Override
            public void onSuccess(List<User> response) {
                mFriendListView.addChat(response);
            }

            @Override
            public void onFailure(String message) {
                mFriendListView.showMessage(message);
                mFriendListView.hideProgressBar();
            }

            @Override
            public void hasChildren(boolean hasChild) {
                mFriendListView.hideProgressBar();
                if(hasChild){
                    mFriendListView.hideEmptyView();
                }else {
                    mFriendListView.showEmptyView();
                }
            }
        };
        mDataSource.getFriends(mPreferenceManager.getUserId(),mUserListDataSourceCallback);
    }
    public void friendClicked(User friend){
        mFriendListView.showFriendDetailView(friend);
    }
}
