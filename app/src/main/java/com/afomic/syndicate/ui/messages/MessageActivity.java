package com.afomic.syndicate.ui.messages;

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
    }

    @Override
    public void setUpView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMessages=new LinkedList<>();
        mMessageAdapter=new MessageAdapter(MessageActivity.this,mMessages,false);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        messageRecyclerView.setAdapter(mMessageAdapter);
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


}
