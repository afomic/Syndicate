package com.afomic.syndicate.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.afomic.syndicate.R;
import com.afomic.syndicate.ui.welcome.WelcomeActivity;

public abstract class BaseActivity extends AppCompatActivity{
    public boolean darkTheme;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPref = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        darkTheme = sharedPref.getBoolean(getString(R.string.key_dark_theme),false);
        setTheme(darkTheme?R.style.darkTheme:R.style.lightTheme);
        super.onCreate(savedInstanceState);
    }

    public void showActivity(Class nextClass){
        Intent intent=new Intent(this,nextClass);
        startActivity(intent);
    }
    public void showActivityAndFinish(Class nextClass){
        showActivity(nextClass);
        finish();
    }
    public String getText(EditText editText){
        return editText.getText().toString();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
