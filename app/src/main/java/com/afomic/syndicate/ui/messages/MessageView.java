package com.afomic.syndicate.ui.messages;

import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.model.Message;

public interface MessageView extends BaseView{
    void updateMessage(Message message, int position);
    int getMessagePosition(Message message);
    void setToolbarTitle(String title);
    void ShowConversation(Message message);
    void resetChatBox();
}
