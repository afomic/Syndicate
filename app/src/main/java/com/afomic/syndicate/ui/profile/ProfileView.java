package com.afomic.syndicate.ui.profile;

import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.model.User;

public interface ProfileView extends BaseView {
    void showEditDialog(String userId,String key,String placeholder);
    void showStatus(String status);
    void showUsername(String username);
    void showId(String id);
}
