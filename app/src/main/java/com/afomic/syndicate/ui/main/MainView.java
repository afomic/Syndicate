package com.afomic.syndicate.ui.main;

import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.model.NavItem;

import java.util.List;

public interface MainView extends BaseView {
    void showProfileView(String userId);
    void showChatListView();
    void showSettingsView();
    void showNavigation(List<NavItem> navItems);
    void notifyDataChange();
}
