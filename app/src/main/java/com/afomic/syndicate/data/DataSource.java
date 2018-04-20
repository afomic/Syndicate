package com.afomic.syndicate.data;

import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.Friend;
import com.afomic.syndicate.model.Message;
import com.afomic.syndicate.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataSource {
    private FirebaseDatabase mFirebaseDatabase;
    @Inject
    public DataSource(){
        mFirebaseDatabase=FirebaseDatabase
                .getInstance();
    }
    public void getChats(String userId, final RealTimeDataSourceCallback<Chat> callback){
        DatabaseReference chatRef=mFirebaseDatabase.getReference(Constants.CHATS_REF)
                .child(userId);
        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void getMessage(String chatId, RealTimeDataSourceCallback<Message> callback){
        mFirebaseDatabase.getReference(Constants.MESSAGES_REF)
                .child(chatId)
                .addChildEventListener(callback);
    }
    public void getUsers(final ListDataSourceCallback<User> callback){
        final List<User> users=new ArrayList<>();
        mFirebaseDatabase.getReference(Constants.USER_REF)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            User user=snapshot.getValue(User.class);
                            users.add(user);
                        }
                        callback.onSuccess(users);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFailure(databaseError.getMessage());
                    }
                });
    }
    public void getFriends(String userId, final ListDataSourceCallback<User> callback){
        final List<User> friends=new ArrayList<>();
        mFirebaseDatabase.getReference(Constants.FRIENDS_REF)
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        callback.hasChildren(dataSnapshot.hasChildren());
                        final long totalFriendCount=dataSnapshot.getChildrenCount();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            final Friend friend=snapshot.getValue(Friend.class);
                            FirebaseDatabase.getInstance()
                                    .getReference(Constants.USER_REF)
                                    .child(friend.getUserID())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            User user=dataSnapshot.getValue(User.class);
                                            friends.add(user);
                                            //all user has been fetched for friends
                                            if(friends.size()==totalFriendCount){
                                                callback.onSuccess(friends);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            if(friends.size()==totalFriendCount){
                                                callback.onFailure(databaseError.getMessage());
                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFailure(databaseError.getMessage());
                    }
                });

    }

    public void getUser(String userId, final SingleItemDataSourceCallback<User> callback){
        mFirebaseDatabase.getReference(Constants.USER_REF)
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
                        callback.onSuccess(user);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFailure(databaseError.getMessage());
                    }
                });
    }
    public void searchUser(String query, final ListDataSourceCallback<User> callback){
        mFirebaseDatabase.getReference(Constants.USER_REF)
                .orderByChild("firstName")
                .equalTo(query)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        callback.hasChildren(dataSnapshot.hasChildren());
                        List<User> users=new ArrayList<>();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            users.add(snapshot.getValue(User.class));
                        }
                        callback.onSuccess(users);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFailure(databaseError.getMessage());
                    }
                });
    }
}
