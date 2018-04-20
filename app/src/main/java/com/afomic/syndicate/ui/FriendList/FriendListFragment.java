package com.afomic.syndicate.ui.FriendList;

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
import com.afomic.syndicate.adapter.FriendAdapter;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FriendListFragment extends Fragment implements FriendListView,FriendAdapter.FriendListener{
    @Inject
    FriendListPresenter mFriendListPresenter;
    @BindView(R.id.rv_friends)
    RecyclerView mFriendsRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_layout)
    LinearLayout mEmptyPageLayout;


    private Unbinder mUnbinder;
    private FriendAdapter mFriendAdapter;
    private List<User> mFriends;
    public static FriendListFragment newInstance(){
       return new FriendListFragment();
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
        View v= inflater.inflate(R.layout.fragment_friend_list,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        mFriendListPresenter.takeView(this);
        mFriendListPresenter.loadData();
        return v;
    }

    @Override
    public void setUpView() {
        mFriends=new ArrayList<>();
        mFriendAdapter=new FriendAdapter(getContext(),mFriends,this);
        mFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFriendsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mFriendsRecyclerView.setAdapter(mFriendAdapter);
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
    public void onClick(User friend) {
        mFriendListPresenter.friendClicked(friend);
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        mFriendListPresenter.dropView();
        super.onDestroy();
    }

    @Override
    public void addChat(List<User> friends) {
        mFriends.clear();
        mFriends.addAll(friends);
        mFriendAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFriendDetailView(User friend) {
    }
}
