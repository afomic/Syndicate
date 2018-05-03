package com.afomic.syndicate.ui.main;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.afomic.syndicate.R;
import com.afomic.syndicate.adapter.NavAdapter;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.model.NavItem;
import com.afomic.syndicate.ui.ChatList.ChatListFragment;
import com.afomic.syndicate.ui.preference.SettingsFragment;
import com.afomic.syndicate.ui.profile.ProfileFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener, MainView,NavAdapter.NavListener{
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.rv_navigation)
    RecyclerView navigationList;
    @BindView(R.id.navigation_layout)
    LinearLayout navigationLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private FragmentManager fm;
    private NavAdapter mNavAdapter;

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
        mMainPresenter.displayNavigation();
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

        if(darkTheme){
            navigationLayout.setBackgroundColor(getResources().getColor(R.color.darkThemeNavigationBarColor));
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
    public void showNavigation(List<NavItem> navItems) {
        mNavAdapter=new NavAdapter(MainActivity.this,navItems,this);
        navigationList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        navigationList.setAdapter(mNavAdapter);
    }

    @Override
    public void notifyDataChange() {
        mNavAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProfileView(String userId) {
        ProfileFragment profileFragment=ProfileFragment.newInstance(userId,true);
        displayFragment(profileFragment);
    }

    @Override
    public void showChatListView() {
        ChatListFragment chatListFragment=ChatListFragment.newInstance();
        displayFragment(chatListFragment);
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

    @Override
    public void onNavigationSelected(int position) {
        mMainPresenter.navigationItemSelected(position);
        mDrawer.closeDrawers();
    }
    @OnClick(R.id.btn_add_account)
    public void addAccount(){
        mDrawer.closeDrawers();
        mMainPresenter.addNewAccount();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(mMainPresenter!=null){
            mMainPresenter.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mMainPresenter.writeState(outState);
        super.onSaveInstanceState(outState);

    }
}
