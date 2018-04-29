package com.afomic.syndicate.ui.ChatList;

import android.view.MenuItem;

import com.afomic.syndicate.R;
import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.ChatDataSource;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.RealTimeDataSourceCallback;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.util.GlideApp;

import javax.inject.Inject;

public class ChatListPresenter implements BasePresenter<ChatListView> {
    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    ChatDataSource mDataSource;
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
            public Class<Chat> getType() {
                return Chat.class;
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
    public void handleMenuItemSelected(MenuItem item){
        if(item.getItemId()== R.id.menu_search){
            mChatListView.showSearchUserView();
        }
    }
    @Override
    public void dropView() {
        mChatRealTimeDataSourceCallback =null;
    }
}
