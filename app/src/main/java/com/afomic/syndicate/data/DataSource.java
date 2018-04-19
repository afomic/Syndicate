package com.afomic.syndicate.data;

import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public void getChats(ListDataSourceCallback<Chat> callback){
    }
    public void getFriends(ListDataSourceCallback<Chat> callback){
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
    }
    public interface SingleItemDataSourceCallback<T> extends DataSourceCallBack {
        void onSuccess(T response);
    }
    public interface ListDataSourceCallback<T> extends DataSourceCallBack {
        void onSuccess(List<T> response);
    }

}
