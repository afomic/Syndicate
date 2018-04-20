package com.afomic.syndicate.ui.ChatList;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.DataSource;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.RealTimeDataSourceCallback;
import com.afomic.syndicate.model.Chat;

import javax.inject.Inject;

public class ChatListPresenter implements BasePresenter<ChatListView> {
    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    DataSource mDataSource;
    private ChatListView mChatListView;
    private RealTimeDataSourceCallback<Chat> mChatRealTimeDataSourceCallback;
    @Inject
    public ChatListPresenter(){

    }
    @Override
    public void takeView(ChatListView view) {
        mChatListView=view;
        mChatListView.setUpView();
    }

    public void loadChats(){
        mChatListView.showProgressBar();
        mChatRealTimeDataSourceCallback =new RealTimeDataSourceCallback<Chat>(){
            @Override
            public void onFailure(String message) {
                mChatListView.hideProgressBar();
                mChatListView.showMessage(message);
            }

            @Override
            public void onNewData(Chat data) {
                mChatListView.addChat(data);
            }

            @Override
            public void onUpdate(Chat data) {
                int position=mChatListView.getChatPosition(data);
                mChatListView.updateChat(data,position);
            }

            @Override
            public void onRemove(Chat data) {

            }

            @Override
            public void hasChildren(boolean hasChild) {
                mChatListView.hideProgressBar();
                if(hasChild){
                    mChatListView.hideEmptyView();
                }else {
                    mChatListView.showEmptyView();
                }
            }
        };
        mDataSource.getChats(mPreferenceManager.getUserId(), mChatRealTimeDataSourceCallback);
    }
    @Override
    public void dropView() {
        mChatRealTimeDataSourceCallback =null;
    }
}
