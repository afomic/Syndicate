package com.afomic.syndicate.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.afomic.syndicate.R;
import com.afomic.syndicate.ui.welcome.WelcomeActivity;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
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

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
