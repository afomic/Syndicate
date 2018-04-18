package com.afomic.syndicate.ui.ChatList;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.PreferenceManager;

import javax.inject.Inject;

public class ChatListPresenter implements BasePresenter<ChatListView> {
    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    public ChatListPresenter(){

    }
    @Override
    public void takeView(ChatListView view) {

    }

    @Override
    public void dropView() {

    }
}
