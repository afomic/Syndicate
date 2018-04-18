package com.example.afomic.syndicate.base;

public interface BasePresenter<V extends BaseView> {
    void takeView(V view );
    void dropView();
}
