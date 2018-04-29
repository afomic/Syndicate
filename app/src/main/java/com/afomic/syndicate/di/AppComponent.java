package com.afomic.syndicate.di;



import com.afomic.syndicate.ui.ChatList.ChatListFragment;
import com.afomic.syndicate.ui.main.MainActivity;
import com.afomic.syndicate.ui.messages.MessageActivity;
import com.afomic.syndicate.ui.multipleAccount.MultipleAccountFragment;
import com.afomic.syndicate.ui.profile.ProfileFragment;
import com.afomic.syndicate.ui.updateProfile.UpdateProfileDialog;
import com.afomic.syndicate.ui.userDetail.UserDetailActivity;
import com.afomic.syndicate.ui.userSearch.UserSearchActivity;
import com.afomic.syndicate.ui.welcome.WelcomeActivity;
import com.afomic.syndicate.ui.welcome.WelcomePresenter;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    WelcomePresenter getWelcomePresenter();
    void inject(WelcomeActivity activity);
    void inject(MainActivity activity);
    void inject(ChatListFragment fragment);
    void inject(ProfileFragment fragment);
    void inject(UpdateProfileDialog dialog);
    void inject(MultipleAccountFragment fragment);
    void inject(UserDetailActivity activity);
    void inject(MessageActivity activity);
    void inject(UserSearchActivity activity);
}
