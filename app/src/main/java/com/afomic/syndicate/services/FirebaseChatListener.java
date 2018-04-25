package com.afomic.syndicate.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by afomic on 2/13/18.
 *
 */

public class FirebaseChatListener extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final PreferenceManager preferenceManager=new PreferenceManager(this);
        FirebaseDatabase
                .getInstance()
                .getReference(Constants.CHATS_REF)
                .child(preferenceManager.getUserId())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final Chat chat=dataSnapshot.getValue(Chat.class);
                        FirebaseDatabase
                                .getInstance()
                                .getReference(Constants.MESSAGES_REF)
                                .child(chat.getId())
                                .addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        Message message=dataSnapshot.getValue(Message.class);
                                        if(message.getSenderId().equals(preferenceManager.getUserId())
                                                ||message.isRead()){

                                        }else {
                                            Intent notificationIntent=new Intent(getApplication(),NotificationReceiver.class);
                                            notificationIntent.putExtra(Constants.EXTRA_MESSAGE,message);
                                            notificationIntent.putExtra(Constants.EXTRA_CHAT,chat);
                                            sendBroadcast(notificationIntent);
                                        }
                                    }

                                    @Override
                                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                    }

                                    @Override
                                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
