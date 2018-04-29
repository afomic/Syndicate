package com.afomic.syndicate.data;

import android.support.annotation.NonNull;

import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import javax.inject.Inject;

public class ChatDataSource {
    private FirebaseDatabase mFirebaseDatabase;
    @Inject
    public ChatDataSource(){
        mFirebaseDatabase=FirebaseDatabase.getInstance();
    }
    public void getChats(String userId, final RealTimeDataSourceCallback<Chat> callback){
        DatabaseReference chatRef=mFirebaseDatabase
                .getReference(Constants.CHATS_REF)
                .child(userId);
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.hasChildren(dataSnapshot.hasChildren());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        chatRef.addChildEventListener(callback);
    }
    public void startChat(String userOne, final String userTwo, final SingleItemDataSourceCallback<Chat> callback){
        String[] ids={userOne,userTwo};
        //chat id should always be the same for two set of people
        Arrays.sort(ids);
        final String chatId=ids[0]+ids[1];
        final Chat chat=new Chat();
        chat.setUserOne(userOne);
        chat.setUserTwo(userTwo);
        chat.setLastUpdate(System.currentTimeMillis());
        chat.setId(chatId);
        mFirebaseDatabase.getReference(Constants.CHATS_REF)
                .child(userOne)
                .child(chatId)
                .setValue(chat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mFirebaseDatabase.getReference(Constants.CHATS_REF)
                                .child(userTwo)
                                .child(chatId)
                                .setValue(chat);
                        callback.onSuccess(chat);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e.getMessage());
            }
        });
    }
    public void getMessage(String chatId,final RealTimeDataSourceCallback<Message> callback){
        DatabaseReference messageRef= mFirebaseDatabase.getReference(Constants.MESSAGES_REF)
                .child(chatId);
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.hasChildren(dataSnapshot.hasChildren());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        messageRef.addChildEventListener(callback);
    }
    public void sendMessage(Message message,String userId,String recipientId,SingleItemDataSourceCallback<Boolean> callback){
        final DatabaseReference messageRef=mFirebaseDatabase.getReference(Constants.MESSAGES_REF)
                .child(message.getChatId());
        final DatabaseReference senderChatRef=mFirebaseDatabase.getReference(Constants.CHATS_REF)
                .child(userId)
                .child(message.getChatId());
        final DatabaseReference recipientChatRef=mFirebaseDatabase.getReference(Constants.CHATS_REF)
                .child(recipientId)
                .child(message.getChatId());
        final String messageId=messageRef.push().getKey();
        message.setId(messageId);


        messageRef.child(messageId)
                .setValue(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        messageRef.child(messageId)
                                .child("delivered")
                                .setValue(true);
                    }
                });
        senderChatRef.child("lastMessage").setValue(message.getMessage());
        senderChatRef.child("lastUpdate").setValue(message.getTime());
        recipientChatRef.child("lastMessage").setValue(message.getMessage());
        recipientChatRef.child("lastUpdate").setValue(message.getTime());
    }
}
