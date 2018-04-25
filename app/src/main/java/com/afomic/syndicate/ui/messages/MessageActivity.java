package com.afomic.syndicate.ui.messages;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.afomic.syndicate.R;
import com.afomic.syndicate.adapter.MessageAdapter;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.model.Message;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity implements MessageView {
    @BindView(R.id.rv_messages)
    RecyclerView messageRecyclerView;
    @BindView(R.id.edt_chat_message)
    EditText messageEditText;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Inject
    MessagePresenter mMessagePresenter;

    private MessageAdapter mMessageAdapter;
    private List<Message> mMessages;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        DependencyInjector.applicationComponent().inject(this);
        mMessagePresenter.takeView(this);
        mMessagePresenter.handleIntent(getIntent());
        mMessagePresenter.loadMessages();
    }

    @Override
    public void setUpView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        mMessages=new LinkedList<>();
        mMessageAdapter=new MessageAdapter(MessageActivity.this,mMessages,darkTheme);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        messageRecyclerView.setAdapter(mMessageAdapter);
        if(darkTheme){
            messageEditText.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void setToolbarTitle(String title) {
        setTitle(title);
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showProgressBar() {
        if(!mProgressBar.isShown()){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        if(mProgressBar.isShown()){
            mProgressBar.setVisibility(View.GONE);
        }
    }
    @OnClick(R.id.btn_send)
    public void sendMessage(){
        mMessagePresenter.sendMessage(getText(messageEditText));
    }

    @Override
    public void updateMessage(Message message, int position) {
        mMessages.remove(position);
        mMessages.add(position,message);
        mMessageAdapter.notifyItemChanged(position,message);
    }

    @Override
    public int getMessagePosition(Message message) {
        for(int i=0;i<mMessages.size();i++){
            if(message.getId().equals(mMessages.get(i).getId())){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void ShowConversation(Message message) {
        mMessages.add(message);
        mMessageAdapter.notifyItemInserted(mMessages.size());
        scrollToTheLastItem();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMessagePresenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMessagePresenter.onResume();
    }

    @Override
    public void resetChatBox() {
        messageEditText.setText("");
    }

    public void scrollToTheLastItem(){
        int lastItemCount=mMessages.size()-1;
        mMessageAdapter.notifyItemInserted(lastItemCount);
        messageRecyclerView.scrollToPosition(lastItemCount);
    }
}
