package com.afomic.syndicate.data;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public interface DataSourceCallback {
    void onFailure(String message);
    void hasChildren(boolean hasChild);

}

