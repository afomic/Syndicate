package com.afomic.syndicate.ui.ChatList;

import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.model.Chat;


public interface ChatListView extends BaseView {
    void showEmptyView();
    void hideEmptyView();
    void addChat(Chat chat);
    void updateChat(Chat chat, int position);
    int getChatPosition(Chat chat);
}
