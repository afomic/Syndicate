package com.afomic.syndicate.ui.messages;

import android.content.Intent;

import com.afomic.syndicate.base.BasePresenter;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.data.RealTimeDataSourceCallback;
import com.afomic.syndicate.data.SingleItemDataSourceCallback;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.Message;
import com.afomic.syndicate.model.User;
import com.afomic.syndicate.data.ChatDataSource;
import com.afomic.syndicate.data.UserDataSource;

import javax.inject.Inject;

public class MessagePresenter implements BasePresenter<MessageView> {
    @Inject
    ChatDataSource mDataSource;
    @Inject
    UserDataSource  mUserDataSource;
    @Inject
    PreferenceManager mPreferenceManager;
    private MessageView mMessageView;
    private Chat currentChat;
    @Inject
    public MessagePresenter(){

    }
    public void handleIntent(Intent intent){
        currentChat=intent.getParcelableExtra(Constants.EXTRA_CHAT);
    }
    @Override
    public void takeView(MessageView view) {
        mMessageView=view;
        mMessageView.setUpView();
    }
    public void loadMessages(){
        mMessageView.showProgressBar();
        mDataSource.getMessage(currentChat.getId(), new RealTimeDataSourceCallback<Message>() {
            @Override
            public Class<Message> getType() {
                return Message.class;
            }

            @Override
            public void onNewData(Message data) {
                mMessageView.ShowConversation(data);
            }

            @Override
            public void onUpdate(Message data) {
                int messagePosition=mMessageView.getMessagePosition(data);
                if(messagePosition!=-1){
                    mMessageView.updateMessage(data,messagePosition);
                }

            }

            @Override
            public void onRemove(Message data) {

            }

            @Override
            public void onFailure(String message) {
                mMessageView.showMessage(message);
            }

            @Override
            public void hasChildren(boolean hasChild) {
                mMessageView.hideProgressBar();
            }
        });
        final String userId;
        if(currentChat.getUserOne().equals(mPreferenceManager.getUserId())){
            userId=currentChat.getUserTwo();
        }else {
            userId=currentChat.getUserOne();
        }
        mUserDataSource.getUser(userId, new SingleItemDataSourceCallback<User>() {
            @Override
            public void onSuccess(User response) {
                mMessageView.setToolbarTitle(response.getUsername());
            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void hasChildren(boolean hasChild) {

            }
        });
    }

    @Override
    public void dropView() {

    }
    public void sendMessage(String message){
        if(message.equals("")){
            mMessageView.showMessage("empty message");
            return;
        }
        mMessageView.resetChatBox();
        Message item=new Message();
        item.setChatId(currentChat.getId());
        item.setTime(System.currentTimeMillis());
        item.setSenderId(mPreferenceManager.getUserId());
        item.setMessage(message);
        String recipientId=mPreferenceManager.getUserId()
                .equals(currentChat.getUserOne())?currentChat.getUserTwo():currentChat.getUserOne();
        mDataSource.sendMessage(item,mPreferenceManager.getUserId(),recipientId, new SingleItemDataSourceCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {

            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void hasChildren(boolean hasChild) {

            }
        });
    }
    public void onPause(){
        mPreferenceManager.setCurrentChatId("");
    }
    public void onResume(){
        mPreferenceManager.setCurrentChatId(currentChat.getId());
    }
}
