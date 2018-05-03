package com.afomic.syndicate.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.afomic.syndicate.R;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.Message;
import com.afomic.syndicate.model.User;
import com.afomic.syndicate.ui.messages.MessageActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 *
 * Created by afomic on 2/14/18.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive( final Context context, final Intent intent) {

        PreferenceManager preferenceManager=new PreferenceManager(context);
        //the new message is for the current chat
        final Chat chat=intent.getParcelableExtra(Constants.EXTRA_CHAT);
        Log.e("onRecieve","i am called");
        if(preferenceManager.getCurrentChatId().equals(chat.getId())){
            return;
        }
        String myUserId=preferenceManager.getUserId();

        //get the sender name
        String senderID= chat.getUserTwo().equals(myUserId)?chat.getUserOne():chat.getUserTwo();
        FirebaseDatabase.getInstance()
                .getReference(Constants.USER_REF)
                .child(senderID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
                        Message message=intent.getParcelableExtra(Constants.EXTRA_MESSAGE);
                        Intent sentIntent=new Intent(context, MessageActivity.class);
                        sentIntent.putExtra(Constants.EXTRA_CHAT,chat);
                        sentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pi= PendingIntent.getActivity(context, 232,sentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        Notification.Builder builder=new Notification.Builder(context);
                        builder.setContentTitle("You have new message from " +user.getUsername());
                        builder.setContentText(message.getMessage());
                        builder.setAutoCancel(true);
                        builder.setPriority(Notification.PRIORITY_MAX);
                        builder.setShowWhen(true);
                        builder.setSmallIcon(R.drawable.ic_email_black_24dp);
                        //make the device vibrate
                        builder.setDefaults(Notification.DEFAULT_VIBRATE);
                        if (Build.VERSION.SDK_INT >= 21){
                            builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
                        }
                        //make sound
                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        builder.setSound(alarmSound);
                        builder.setContentIntent(pi);
                        builder.setWhen(System.currentTimeMillis());
                        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(context);
                        //get a unique id for each chat
                        int notificationId=getNotificationId(chat.getId());
                        managerCompat.notify(notificationId, builder.build());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //build the notification for the new message

    }
    public int getNotificationId(String chatId){
        int notificationId=0;
        for(int i=0;i<chatId.length();i++ ){
            notificationId=chatId.charAt(i)+i;
        }
        return notificationId;
    }
}
