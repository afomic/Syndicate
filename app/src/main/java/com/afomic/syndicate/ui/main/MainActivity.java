package com.afomic.syndicate.ui.main;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.afomic.syndicate.R;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.ui.ChatList.ChatListFragment;
import com.afomic.syndicate.ui.multipleAccount.MultipleAccountFragment;
import com.afomic.syndicate.ui.preference.SettingsFragment;
import com.afomic.syndicate.ui.profile.ProfileFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener, MainView{
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
        DependencyInjector
                .applicationComponent()
                .inject(this);
        mMainPresenter.takeView(this);
    }
    @Override
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
        if(darkTheme){
            mNavigationView.setBackgroundColor(getResources().getColor(R.color.darkThemeNavigationBarColor));
        }
    }
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.key_dark_theme))) {
            recreate();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

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

    @Override
    public void showMultipleAccountView() {
        MultipleAccountFragment multipleAccountFragment=MultipleAccountFragment.newInstance();
        displayFragment(multipleAccountFragment);
    }

    @Override
    public void showSettingsView() {
        SettingsFragment settingsFragment=new SettingsFragment();
        displayFragment(settingsFragment);
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
