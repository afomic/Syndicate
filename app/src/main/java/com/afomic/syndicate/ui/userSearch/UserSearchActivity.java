package com.afomic.syndicate.ui.userSearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.afomic.syndicate.R;
import com.afomic.syndicate.adapter.UserAdapter;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.model.User;
import com.afomic.syndicate.ui.userDetail.UserDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserSearchActivity extends BaseActivity implements UserSearchView,UserAdapter.UserListener {
    @BindView(R.id.rv_users)
    RecyclerView userRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_layout)
    LinearLayout mEmptyLayout;

    @Inject
    UserSearchPresenter mUserSearchPresenter;

    private List<User> mUsers;
    private UserAdapter mUserAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        ButterKnife.bind(this);
        DependencyInjector.applicationComponent()
                .inject(this);
        mUserSearchPresenter.takeView(this);
    }
    @Override
    public void setUpView() {
        mUsers=new ArrayList<>();
        userRecyclerView.setLayoutManager(new LinearLayoutManager(UserSearchActivity.this));
        mUserAdapter=new UserAdapter(UserSearchActivity.this,mUsers,this);
        userRecyclerView.addItemDecoration(new DividerItemDecoration(UserSearchActivity.this,
                DividerItemDecoration.VERTICAL));
        userRecyclerView.setAdapter(mUserAdapter);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
    }

    @Override
    public void showSearchResult(List<User> users) {
        mUsers.clear();
        mUsers.addAll(users);
        mUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyLayout() {
        if(!mEmptyLayout.isShown()){
            mEmptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideEmptyLayout() {
        if(mEmptyLayout.isShown()){
            mEmptyLayout.setVisibility(View.GONE);
        }
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

    @Override
    public void onClick(User friend) {
        mUserSearchPresenter.userClicked(friend);
    }
    @Override
    public void showUserDetailView(User user){
        Intent intent=new Intent(UserSearchActivity.this, UserDetailActivity.class);
        intent.putExtra(Constants.EXTRA_USER,user);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_search,menu);
        MenuItem mItem=menu.findItem(R.id.menu_search_view);
        SearchView userSearchView=(SearchView) MenuItemCompat.getActionView(mItem);
        ImageView mImage= userSearchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        userSearchView.setIconified(false);
        userSearchView.setIconifiedByDefault(false);
        mImage.setVisibility(View.GONE);
        mImage.setImageDrawable(null);
        userSearchView.setQueryHint("Search User");

        userSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.equals("")){
                    mUserSearchPresenter.searchUserByFirstName(query);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
