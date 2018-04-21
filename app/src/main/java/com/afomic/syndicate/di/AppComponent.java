package com.afomic.syndicate.di;



import com.afomic.syndicate.ui.ChatList.ChatListFragment;
import com.afomic.syndicate.ui.FriendList.FriendListFragment;
import com.afomic.syndicate.ui.main.MainActivity;
import com.afomic.syndicate.ui.login.LoginActivity;
import com.afomic.syndicate.ui.login.LoginPresenter;
import com.afomic.syndicate.ui.messages.MessageActivity;
import com.afomic.syndicate.ui.profile.ProfileFragment;
import com.afomic.syndicate.ui.profile.ProfileView;
import com.afomic.syndicate.ui.signUp.SignUpActivity;
import com.afomic.syndicate.ui.userDetail.UserDetailActivity;
import com.afomic.syndicate.ui.welcome.WelcomeActivity;
import com.afomic.syndicate.ui.welcome.WelcomePresenter;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    LoginPresenter getLoginPresenter();
    WelcomePresenter getWelcomePresenter();
    void inject(WelcomeActivity activity);
    void inject(LoginActivity activity);
    void inject(SignUpActivity activity);
    void inject(MainActivity activity);
    void inject(ChatListFragment fragment);
    void inject(FriendListFragment fragment);
    void inject(ProfileFragment fragment);
    void inject(UserDetailActivity activity);
    void inject(MessageActivity activity);
}
