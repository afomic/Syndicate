package com.afomic.syndicate.ui.messages;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.DataSource;

import javax.inject.Inject;

public class MessagePresenter implements BasePresenter<MessageView> {
    @Inject
    DataSource mDataSource;


    private MessageView mMessageView;
    @Inject
    public MessagePresenter(){

    }
    @Override
    public void takeView(MessageView view) {
        mMessageView=view;
        mMessageView.setUpView();
    }

    @Override
    public void dropView() {

    }
}
