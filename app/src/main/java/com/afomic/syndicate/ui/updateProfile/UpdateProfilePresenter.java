package com.afomic.syndicate.ui.updateProfile;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.SingleItemDataSourceCallback;
import com.afomic.syndicate.data.UserDataSource;

import javax.inject.Inject;

public class UpdateProfilePresenter implements BasePresenter<UpdateProfileView> {
    @Inject
    UserDataSource mUserDataSource;

    private String userId,key,placeholder;
    private UpdateProfileView mUpdateProfileView;
    @Inject
    public UpdateProfilePresenter(){

    }
    public void takeData(String userId,String key,String placeholder ){
        this.userId=userId;
        this.key=key;
        this.placeholder=placeholder;
    }
    public void initializeView(){
        mUpdateProfileView.setHint(key);
        String title="Update "+key;
        mUpdateProfileView.setTitle(title);
        mUpdateProfileView.setPlaceHolder(placeholder);
    }
    public void saveValue(final String value){
        if(value.length()==0){
            mUpdateProfileView.showMessage("Empty "+key);
        }else {
            mUpdateProfileView.showProgressBar();
            mUserDataSource.setUserDetail(userId, key, value, new SingleItemDataSourceCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean response) {
                    mUpdateProfileView.hideProgressBar();
                    mUpdateProfileView.showMessage(key+" Successfully update");
                    mUpdateProfileView.dismissDialog();
                    mUpdateProfileView.setDataToFragment(key,value);
                }

                @Override
                public void onFailure(String message) {
                    mUpdateProfileView.showMessage(key+" Failed to update");
                    mUpdateProfileView.hideProgressBar();
                }

                @Override
                public void hasChildren(boolean hasChild) {

                }
            });
        }

    }

    @Override
    public void takeView(UpdateProfileView view) {
        this.mUpdateProfileView=view;
    }

    @Override
    public void dropView() {

    }

}
