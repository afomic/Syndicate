package com.afomic.syndicate.ui.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.afomic.syndicate.R;
import com.afomic.syndicate.base.BaseActivity;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity implements WelcomeView{
    @BindView(R.id.creating_account_layout)
    LinearLayout setupAccountLayout;
    @Inject
    WelcomePresenter welcomePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        DependencyInjector
                .applicationComponent()
                .inject(this);
        welcomePresenter.takeView(this);
        welcomePresenter.showNextActivity();
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void showLoadingLayout() {
        setupAccountLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showHomeView() {
        showActivityAndFinish(MainActivity.class);
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
