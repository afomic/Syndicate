package com.afomic.syndicate.ui.multipleAccount;

import com.afomic.syndicate.base.BaseView;
import com.afomic.syndicate.model.User;

import java.util.List;

public interface MultipleAccountView extends BaseView {
     void showAccounts(List<User> userAccounts);
     void showUserAccountDetailView(User userAccount);

}
