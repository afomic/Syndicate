package com.afomic.syndicate.ui.profile;

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
import android.widget.TextView;
import android.widget.Toast;

import com.afomic.syndicate.R;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.model.User;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProfileFragment extends Fragment implements ProfileView {
    @BindView(R.id.tv_user_name)
    TextView usernameTextView;
    @BindView(R.id.tv_user_status)
    TextView statusTextView;
    @Inject
    ProfilePresenter mProfilePresenter;

    private Unbinder mUnbinder;

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        DependencyInjector.applicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_account,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProfile(User user) {
        statusTextView.setText(user.getStatus());
        usernameTextView.setText(user.getUsername());
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setUpView() {

    }
}
