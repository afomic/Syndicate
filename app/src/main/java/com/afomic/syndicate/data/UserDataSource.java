package com.afomic.syndicate.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.afomic.syndicate.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

public class UserDataSource {
    private FirebaseDatabase mFirebaseDatabase;
    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    public UserDataSource(){
        mFirebaseDatabase=FirebaseDatabase.getInstance();
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
    public void setUserDetail(String userId, String key, String value, final SingleItemDataSourceCallback<Boolean> callback){
        mFirebaseDatabase.getReference(Constants.USER_REF)
                .child(userId)
                .child(key)
                .setValue(value)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e.getMessage());
            }
        });
    }
    public void saveUser(String accountUniqueId, final SingleItemDataSourceCallback<Boolean> callback){
        User user=new User();
        DatabaseReference userDataBaseRef=mFirebaseDatabase.getReference(Constants.USER_REF);
        String userId=userDataBaseRef.push().getKey();
        user.setId(userId);
        user.setUniqueId(accountUniqueId);
        String username="user-"+getRandomString(6);
        user.setUsername(username);
        user.setTimeCreated(System.currentTimeMillis());
        user.setStatus("I am a new User");
        userDataBaseRef
                .child(userId)
                .setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e.getMessage());
            }
        });

    }
    public void getUserAccounts(String uniqueId, final ListDataSourceCallback<User> callback){
        mFirebaseDatabase.getReference(Constants.USER_REF)
                .orderByChild("uniqueId")
                .equalTo(uniqueId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> users=new ArrayList<>();
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            User user=snapshot.getValue(User.class);
                            users.add(user);
                        }
                        callback.hasChildren(dataSnapshot.hasChildren());
                        callback.onSuccess(users);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFailure(databaseError.getMessage());
                    }
                });
    }
    public void setHasMultipleAccount(String uniqueId){
        mFirebaseDatabase.getReference(Constants.USER_REF)
                .orderByChild("uniqueId")
                .equalTo(uniqueId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()>1){
                            mPreferenceManager.setHasMultipleAccount(true);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("HasMultipleAccount","HasMultipleAccount: Setting value failed: "+databaseError.getMessage());
                    }
                });
    }
    public void searchUser(String query, final ListDataSourceCallback<User> callback){
        mFirebaseDatabase.getReference(Constants.USER_REF)
                .orderByChild("username")
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
    public static String getRandomString(int lenght) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < lenght) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }
}
