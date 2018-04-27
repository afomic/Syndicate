package com.afomic.syndicate.ui.multipleAccount;

import android.gesture.Prediction;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.DataSource;
import com.afomic.syndicate.data.PreferenceManager;

import javax.inject.Inject;

public class MultipleAccountPresenter implements BasePresenter<MultipleAccountView> {
    @Inject
    DataSource mDataSource;
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

    }

    @Override
    public void dropView() {

    }
}
