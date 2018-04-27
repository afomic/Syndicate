package com.afomic.syndicate.ui.multipleAccount;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.afomic.syndicate.R;
import com.afomic.syndicate.di.DependencyInjector;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MultipleAccountFragment extends Fragment  implements MultipleAccountView{
    @BindView(R.id.rv_accounts)
    RecyclerView accountRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Inject
    MultipleAccountPresenter mMultipleAccountPresenter;

    private Unbinder mUnbinder;

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
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_account,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setUpView() {

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
}
