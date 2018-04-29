package com.afomic.syndicate.ui.main;

import com.afomic.syndicate.base.BaseView;

public interface MainView extends BaseView {
    void showProfileView(String userId);
    void showChatListView();
    void showSettingsView();
    void showMultipleAccountView();
}
