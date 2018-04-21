package com.afomic.syndicate.ui.userSearch;

import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.model.User;

import java.util.List;

public interface UserSearchView extends BaseView {
    void showSearchResult(List<User> users);
    void showEmptyLayout();
    void hideEmptyLayout();
    void showUserDetailView(User user);
}
