package com.afomic.syndicate.ui.ChatList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afomic.syndicate.R;
import com.afomic.syndicate.adapter.ChatAdapter;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.ui.messages.MessageActivity;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatListFragment extends Fragment implements ChatListView {
    @Inject
    ChatListPresenter mChatListPresenter;
    @BindView(R.id.rv_chats)
    RecyclerView chatsRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_layout)
    LinearLayout mEmptyPageLayout;

    private Unbinder mUnbinder;
    private List<Chat> mChats;
    private ChatAdapter mChatAdapter;

    public static ChatListFragment newInstance(){
        return new ChatListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        DependencyInjector
                .applicationComponent()
                .inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_chat_list,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        mChatListPresenter.takeView(this);
        mChatListPresenter.loadChats();
        return v;
    }
    @Override
    public void setUpView(){
        mChats=new LinkedList<>();
        mChatAdapter=new ChatAdapter(getContext(),mChats);
        chatsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        chatsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatsRecyclerView.setAdapter(mChatAdapter);

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mChatListPresenter.dropView();
        mUnbinder.unbind();
    }

    @Override
    public void showEmptyView() {
        if(!mEmptyPageLayout.isShown()){
            mEmptyPageLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideEmptyView() {
        if(mEmptyPageLayout.isShown()){
            mEmptyPageLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void addChat(Chat chat) {
        mChats.add(chat);
        mChatAdapter.notifyItemInserted(mChats.size());
    }

    @Override
    public void updateChat(Chat chat, int position) {
        mChats.remove(position);
        mChats.add(position,chat);
        mChatAdapter.notifyItemChanged(position);
    }

    @Override
    public int getChatPosition(Chat chat) {
        for(int i=0;i<mChats.size();i++){
            if(mChats.get(i).getId().equals(chat.getId())){
                return i;
            }
        }
        return -1;
    }

}
