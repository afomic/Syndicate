package com.afomic.syndicate.ui.multipleAccount;

import android.util.Log;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.ListDataSourceCallback;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.SingleItemDataSourceCallback;
import com.afomic.syndicate.data.UserDataSource;
import com.afomic.syndicate.model.User;

import java.util.List;

import javax.inject.Inject;

public class MultipleAccountPresenter implements BasePresenter<MultipleAccountView> {
    @Inject
    UserDataSource mDataSource;
    @Inject
    PreferenceManager mPreferenceManager;
    private MultipleAccountView mMultipleAccountView;
    @Inject
    public MultipleAccountPresenter(){

    }
    @Override
    public void takeView(MultipleAccountView view) {
        mMultipleAccountView=view;
        mMultipleAccountView.setUpView();
    }
    public void fetchAccounts(){
        mMultipleAccountView.showProgressBar();
        mDataSource.getUserAccounts(mPreferenceManager.getUniqueId(), new ListDataSourceCallback<User>() {
            @Override
            public void onSuccess(List<User> response) {
                mMultipleAccountView.showAccounts(response);
            }

            @Override
            public void onFailure(String message) {
                mMultipleAccountView.showMessage(message);
            }

            @Override
            public void hasChildren(boolean hasChild) {
                mMultipleAccountView.hideProgressBar();
            }
        });
    }
    public void addNewAccount(){
        mMultipleAccountView.showProgressBar();
        Log.e("unique","unique id:"+mPreferenceManager.getUniqueId());
        mDataSource.saveUser(mPreferenceManager.getUniqueId(), new SingleItemDataSourceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                mMultipleAccountView.hideProgressBar();
                if(response){
                    mMultipleAccountView.showMessage("New Account created");
                    mDataSource.setHasMultipleAccount(mPreferenceManager.getUniqueId());
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
    public void userAccountSelected(User user){
        mMultipleAccountView.showUserAccountDetailView(user);
    }

    @Override
    public void dropView() {

    }
}
