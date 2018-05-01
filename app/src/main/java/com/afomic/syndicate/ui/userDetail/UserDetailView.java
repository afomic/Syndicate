package com.afomic.syndicate.ui.userDetail;

import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.User;

public interface UserDetailView extends BaseView {
    void showMessageView(Chat chat);
    void enableControls(boolean enable);
    void setUserId(String userId);
    void setStatus(String status);
    void setUsername(String username);
    void showEditButtons();
    void hideEditButtons();
    void hideChatButton();
    void enableSetAccountButton(boolean enable);
    void showSetAccountButton();
    void hideSetAccountButton();
}
