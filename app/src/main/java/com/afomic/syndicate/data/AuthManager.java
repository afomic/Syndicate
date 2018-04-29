package com.afomic.syndicate.data;

import android.support.annotation.NonNull;

import com.afomic.syndicate.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthManager {
    private FirebaseAuth mFirebaseAuth;
    @Inject
    PreferenceManager mPreferenceManager;
    @Inject
    public AuthManager(){
       mFirebaseAuth=FirebaseAuth.getInstance();
    }
    public void signUp(final AuthManagerCallback authManagerCallback){
        mFirebaseAuth.signInAnonymously()
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String id=authResult.getUser().getUid();
                        User user=new User();
                        user.setId(id);
                        String username="User-"+getRandomString();
                        user.setUsername(username);
                        user.setStatus("I am a new User");
                        user.setTimeCreated(System.currentTimeMillis());
                        saveUser(user,authManagerCallback);
                        mPreferenceManager.setLoggedIn(true);
                        mPreferenceManager.setUserId(id);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                authManagerCallback.onFailure(e.getMessage());
            }
        });

    }
    public void logout(){
        mFirebaseAuth.signOut();
        mPreferenceManager.setLoggedIn(false);
    }
    private void saveUser(final User user, final AuthManagerCallback callback){
        FirebaseDatabase.getInstance()
                .getReference(Constants.USER_REF)
                .child(mPreferenceManager.getUniqueId())
                .child(user.getId())
                .setValue(user)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mPreferenceManager.setLoggedIn(true);
                mPreferenceManager.setUserId(user.getId());
                callback.onSuccess();
            }
        });
    }
    public interface AuthManagerCallback{
        void onSuccess();
        void onFailure(String message);
    }
    public static String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }
}
