package com.afomic.syndicate.ui.FriendList;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.DataSource;
import com.afomic.syndicate.model.User;

import javax.inject.Inject;

public class FriendListPresenter implements BasePresenter<FriendListView> {
    @Inject
    DataSource mDataSource;
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

    }
    public void loadData(){

    }
    public void friendClicked(User friend){
        mFriendListView.showFriendDetailView(friend);
    }
}
