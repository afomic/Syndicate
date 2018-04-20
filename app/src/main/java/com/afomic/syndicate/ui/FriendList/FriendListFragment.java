package com.afomic.syndicate.ui.FriendList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afomic.syndicate.R;
import com.afomic.syndicate.di.DependencyInjector;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FriendListFragment extends Fragment implements FriendListView{
    @Inject
    FriendListPresenter mFriendListPresenter;
    @BindView(R.id.rv_friends)
    RecyclerView mFriendsRecyclerView;

    private Unbinder mUnbinder;
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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        mFriendListPresenter.dropView();
        super.onDestroy();
    }
}
