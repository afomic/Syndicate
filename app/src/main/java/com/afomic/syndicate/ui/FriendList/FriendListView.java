package com.afomic.syndicate.ui.FriendList;

import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.model.User;

import java.util.List;

public interface FriendListView extends BaseView {
    void showEmptyView();
    void hideEmptyView();
    void addChat(List<User> friends);
    void showFriendDetailView(User friend);
    void showUserSearchView();
}
