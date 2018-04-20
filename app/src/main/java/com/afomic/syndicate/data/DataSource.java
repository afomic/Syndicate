package com.afomic.syndicate.data;

import android.util.Log;

import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.Friend;
import com.afomic.syndicate.model.Message;
import com.afomic.syndicate.model.User;
import com.google.firebase.database.ChildEventListener;
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
    public void getChats(String userId, final RealTimeSourceCallback<Chat> callback){
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
    public void getMessage(String chatId, RealTimeSourceCallback<Message> callback){
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
    public interface DataSourceCallBack {
        void onFailure(String message);
        void hasChildren(boolean hasChild);
    }
    public interface SingleItemDataSourceCallback<T> extends DataSourceCallBack {
        void onSuccess(T response);
    }
    public interface ListDataSourceCallback<T> extends DataSourceCallBack {
        void onSuccess(List<T> response);
    }
    public abstract static class RealTimeSourceCallback<T> implements DataSourceCallBack,ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            try{
                @SuppressWarnings("unchecked")
                T data=(T)dataSnapshot.getValue();
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
                T data=(T)dataSnapshot.getValue();
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
                T data=(T)dataSnapshot.getValue();
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

        public abstract void onNewData(T data);
        public abstract void onUpdate(T data);
        public abstract void onRemove(T data);
    }

}
