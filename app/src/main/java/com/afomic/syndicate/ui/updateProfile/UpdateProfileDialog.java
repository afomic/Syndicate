package com.afomic.syndicate.ui.updateProfile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.afomic.syndicate.R;
import com.afomic.syndicate.di.DependencyInjector;

import butterknife.BindView;

public class UpdateProfileDialog extends DialogFragment implements UpdateProfileView {
    private static final String BUNDLE_USER_ID="user_id";
    private static final String BUNDLE_KEY="key";

    @BindView(R.id.edt_value)
    EditText valueEditText;

    private String key,userId;
    private AlertDialog.Builder builder;
    public UpdateProfileDialog newInstance(String userId,String key){
        Bundle args=new Bundle();
        args.putString(BUNDLE_KEY,key);
        args.putString(BUNDLE_USER_ID,userId);
        UpdateProfileDialog dialog=new UpdateProfileDialog();
        dialog.setArguments(args);
        return dialog;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DependencyInjector.applicationComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder=new AlertDialog.Builder(getActivity());
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_profile,
                null,
                false);
        return builder.create();
    }

    @Override
    public void setTitle(String title) {
        builder.setTitle(title);
    }

    @Override
    public void setPlaceHolder(String placeHolder) {
        valueEditText.setText(placeHolder);
    }

    @Override
    public void setHint(String hint) {
        valueEditText.setHint(hint);
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
