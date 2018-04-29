package com.afomic.syndicate.ui.updateProfile;

import com.afomic.syndicate.base.BaseView;

public interface UpdateProfileView extends BaseView {
    void setTitle(String title);
    void setPlaceHolder(String placeHolder);
    void setHint(String hint);
    void dismissDialog();
    void setDataToFragment(String key,String newValue);
}
