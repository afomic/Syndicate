package com.afomic.syndicate.ui.updateProfile;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afomic.syndicate.R;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.di.DependencyInjector;
import com.afomic.syndicate.ui.profile.ProfileFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class UpdateProfileDialog extends DialogFragment implements UpdateProfileView {
    private static final String BUNDLE_USER_ID="user_id";
    private static final String BUNDLE_KEY="key";
    private static final String BUNDLE_PLACEHOLDER="placeholder";

    @BindView(R.id.edt_value)
    EditText valueEditText;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Inject
    UpdateProfilePresenter mUpdateProfilePresenter;
    private AlertDialog.Builder builder;
    public static UpdateProfileDialog newInstance(String userId,String key,String placeHolder){
        Bundle args=new Bundle();
        args.putString(BUNDLE_KEY,key);
        args.putString(BUNDLE_USER_ID,userId);
        args.putString(BUNDLE_PLACEHOLDER,placeHolder);
        UpdateProfileDialog dialog=new UpdateProfileDialog();
        dialog.setArguments(args);
        return dialog;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DependencyInjector.applicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        String userId=args.getString(BUNDLE_USER_ID);
        String placeholder=args.getString(BUNDLE_PLACEHOLDER);
        String key=args.getString(BUNDLE_KEY);
        mUpdateProfilePresenter.takeData(userId,key,placeholder);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder=new AlertDialog.Builder(getActivity());
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_profile,
                null,
                false);
        builder.setView(v);
        ButterKnife.bind(this,v);
        mUpdateProfilePresenter.takeView(this);
        mUpdateProfilePresenter.initializeView();
        return builder.create();
    }

    @OnClick(R.id.btn_dialog_cancel)
    public void cancelUpdate(){
        dismiss();
    }
    @OnClick(R.id.btn_dialog_submit)
    public void submitUpdate(){
        String value=valueEditText.getText().toString();
        mUpdateProfilePresenter.saveValue(value);
    }

    @Override
    public void setTitle(String title) {
        builder.setTitle(title);
    }

    @Override
    public void dismissDialog() {
        dismiss();
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
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        if(!mProgressBar.isShown()){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        if(mProgressBar.isShown()){
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setDataToFragment(String key, String newValue) {
        Intent intent=new Intent();
        intent.putExtra(Constants.EXTRA_KEY,key);
        intent.putExtra(Constants.EXTRA_NEW_VALUE,newValue);
        getTargetFragment().onActivityResult(ProfileFragment.UPDATE_USER_REQUEST_CODE,
                RESULT_OK,intent);
    }
}
