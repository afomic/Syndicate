package com.afomic.syndicate.ui.main;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.afomic.syndicate.R;
import com.afomic.syndicate.Syndicate;
import com.afomic.syndicate.ui.ChatList.ChatListFragment;
import com.afomic.syndicate.ui.FriendList.FriendListFragment;
import com.afomic.syndicate.ui.profile.ProfileFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView{
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    FragmentManager fm;

    @Inject
    MainPresenter mMainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((Syndicate)getApplication())
                .getComponent()
                .inject(this);
        mMainPresenter.takeView(this);
        setUpView();
    }
    public void setUpView(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Home");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze);
        }
        fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_container);
        if (fragment == null) {
            ChatListFragment chatListFragment = ChatListFragment.newInstance();
            fm.beginTransaction().add(R.id.main_container, chatListFragment)
                    .commit();
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mMainPresenter.navigationItemSelected(item);
                return true;
            }
        });
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
    public void showFriendListView() {
        FriendListFragment friendListFragment=FriendListFragment.newInstance();
        displayFragment(friendListFragment);
    }

    @Override
    public void showProfileView() {
        ProfileFragment profileFragment=ProfileFragment.newInstance();
        displayFragment(profileFragment);
    }

    @Override
    public void showChatListView() {
        ChatListFragment chatListFragment=ChatListFragment.newInstance();
        displayFragment(chatListFragment);
    }
    private void displayFragment(Fragment frag){
        mDrawer.closeDrawers();
        fm.beginTransaction().replace(R.id.main_container,frag).commit();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(Gravity.START,true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
