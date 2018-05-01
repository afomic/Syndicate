package com.afomic.syndicate.ui.multipleAccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afomic.syndicate.R;
import com.afomic.syndicate.adapter.UserAdapter;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.model.User;
import com.afomic.syndicate.ui.userDetail.UserDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MultipleAccountFragment extends Fragment  implements MultipleAccountView, UserAdapter.UserListener{
    @BindView(R.id.rv_accounts)
    RecyclerView accountRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Inject
    MultipleAccountPresenter mMultipleAccountPresenter;

    private Unbinder mUnbinder;
    private UserAdapter mUserAdapter;
    private List<User> mUsers;

    public static MultipleAccountFragment newInstance(){
        return new MultipleAccountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        DependencyInjector.applicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_multiple_account,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        mMultipleAccountPresenter.takeView(this);
        mMultipleAccountPresenter.fetchAccounts();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_account,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_add_account){
            mMultipleAccountPresenter.addNewAccount();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUpView() {
        mUsers=new ArrayList<>();
        mUserAdapter=new UserAdapter(getContext(),mUsers,this);
        accountRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        accountRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        accountRecyclerView.setAdapter(mUserAdapter);


    }

    @Override
    public void showAccounts(List<User> userAccounts) {
        mUsers.clear();
        mUsers.addAll(userAccounts);
        mUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        if(mProgressBar!=null&&!mProgressBar.isShown()){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        if(mProgressBar!=null&&mProgressBar.isShown()){
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showUserAccountDetailView(User userAccount) {
        Intent intent=new Intent(getContext(), UserDetailActivity.class);
        intent.putExtra(Constants.EXTRA_USER,userAccount);
        intent.putExtra(Constants.EXTRA_MY_ACCOUNT,true);
        startActivity(intent);
    }

    @Override
    public void onClick(User friend) {
        mMultipleAccountPresenter.userAccountSelected(friend);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mMultipleAccountPresenter.dropView();
    }
}
