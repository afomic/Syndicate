package com.afomic.syndicate.data;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public abstract class RealTimeDataSourceCallback<T>
        implements DataSourceCallback,ChildEventListener {

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        try{
            @SuppressWarnings("unchecked")
            T data=dataSnapshot.getValue(getType());
            onNewData(data);
        }catch (ClassCastException e){
            e.printStackTrace();
            onFailure(e.getMessage());
        }

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        try{
            @SuppressWarnings("unchecked")
            T data=dataSnapshot.getValue(getType());
            onUpdate(data);
        }catch (ClassCastException e){
            e.printStackTrace();
            onFailure(e.getMessage());
        }

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        try{
            @SuppressWarnings("unchecked")
            T data=dataSnapshot.getValue(getType());
            onRemove(data);
        }catch (ClassCastException e){
            e.printStackTrace();
            onFailure(e.getMessage());
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        onFailure(databaseError.getMessage());
        Log.e("tag", "onCancelled: "+databaseError.getMessage());

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }
    public abstract Class<T> getType();
    public abstract void onNewData(T data);
    public abstract void onUpdate(T data);
    public abstract void onRemove(T data);
}