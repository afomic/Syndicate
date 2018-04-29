package com.afomic.syndicate.ui.userSearch;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.ListDataSourceCallback;
import com.afomic.syndicate.data.UserDataSource;
import com.afomic.syndicate.model.User;

import java.util.List;

import javax.inject.Inject;

public class UserSearchPresenter implements BasePresenter<UserSearchView> {
    @Inject
    UserDataSource mDataSource;

    private UserSearchView mUserSearchView;

    @Inject
    public UserSearchPresenter(){

    }
    @Override
    public void takeView(UserSearchView view) {
        this.mUserSearchView=view;
        mUserSearchView.setUpView();
    }

    @Override
    public void dropView() {

    }
    public void searchUserByFirstName(String user){
        mUserSearchView.showProgressBar();
        mDataSource.searchUser(user, new ListDataSourceCallback<User>() {
            @Override
            public void onSuccess(List<User> response) {
                mUserSearchView.showSearchResult(response);
            }

            @Override
            public void onFailure(String message) {
                mUserSearchView.showMessage(message);
            }

            @Override
            public void hasChildren(boolean hasChild) {
                mUserSearchView.hideProgressBar();
                if(hasChild){
                    mUserSearchView.hideEmptyLayout();
                }else {
                    mUserSearchView.showEmptyLayout();
                }
            }
        });
    }
    public void userClicked(User user){
        mUserSearchView.showUserDetailView(user);
    }
}
