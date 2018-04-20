package com.afomic.syndicate.ui.profile;

import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.model.User;

public interface ProfileView extends BaseView {
    void showProfile(User user);
}
