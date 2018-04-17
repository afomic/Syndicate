package com.example.afomic.syndicate;

public interface BasePresenter<V extends BaseView> {
    void takeView(V view );
    void dropView();
}
