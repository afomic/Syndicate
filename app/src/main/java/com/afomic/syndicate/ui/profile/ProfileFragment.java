package com.afomic.syndicate.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afomic.syndicate.R;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.ui.updateProfile.UpdateProfileDialog;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements ProfileView {
    @BindView(R.id.tv_user_name)
    TextView usernameTextView;
    @BindView(R.id.tv_user_status)
    TextView statusTextView;
    @BindView(R.id.tv_user_id)
    TextView userIdTextView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Inject
    ProfilePresenter mProfilePresenter;

    private static final String BUNDLE_USER_ID="user_id";
    public static int UPDATE_USER_REQUEST_CODE=100;
    private Unbinder mUnbinder;
    private String userId;

    public static ProfileFragment newInstance(String userId) {
        Bundle args= new Bundle();
        args.putString(BUNDLE_USER_ID,userId);
        ProfileFragment fragment=new ProfileFragment();;
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        DependencyInjector.applicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        userId=getArguments().getString(BUNDLE_USER_ID);
        mProfilePresenter.setUserId(userId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile,container,false);
        mUnbinder= ButterKnife.bind(this,v);
        mProfilePresenter.takeView(this);
        mProfilePresenter.loadData();
        return v;
    }
    @OnClick(R.id.btn_edit_display_name)
    public void editDisplayName(){
        mProfilePresenter.handleDisplayNameEdit();
    }
    @OnClick(R.id.btn_edit_status)
    public void editStatus(){
        mProfilePresenter.handleStatusEdit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_account,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mProfilePresenter.handleMenuSelected(item);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEditDialog(String userId, String key, String placeholder) {
        UpdateProfileDialog dialog=UpdateProfileDialog.newInstance(userId,key,placeholder);
        dialog.setTargetFragment(this,UPDATE_USER_REQUEST_CODE);
        dialog.show(getFragmentManager(),"");
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
        mProfilePresenter.dropView();
        mUnbinder.unbind();
    }

    @Override
    public void showStatus(String status) {
        statusTextView.setText(status);
    }

    @Override
    public void showUsername(String username) {
        usernameTextView.setText(username);
    }

    @Override
    public void showId(String id) {
        userIdTextView.setText(id);
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==UPDATE_USER_REQUEST_CODE&&resultCode==RESULT_OK){
            mProfilePresenter.handleIntent(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
