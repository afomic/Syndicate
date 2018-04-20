package com.afomic.syndicate.ui.userDetail;

import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.model.User;

public interface UserDetailView extends BaseView {
    void showUserDetails(User user);
}
