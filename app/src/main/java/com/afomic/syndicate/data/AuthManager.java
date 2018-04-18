package com.afomic.syndicate.data;

import android.support.annotation.NonNull;

import com.afomic.syndicate.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
    public void login(String email, String password, final AuthManagerCallback authManagerCallback){
        mFirebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authManagerCallback.onSuccess();
                        mPreferenceManager.setLoggedIn(true);
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
    public void createUser(final User user, String email, String password,
                           final AuthManagerCallback authManagerCallback){
        mFirebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String id=authResult.getUser().getProviderId();
                        user.setId(id);
                        saveUser(user,authManagerCallback);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                authManagerCallback.onFailure(e.getMessage());
            }
        });

    }
    private void saveUser(User user, final AuthManagerCallback callback){
        FirebaseDatabase.getInstance()
                .getReference(Constants.USER_REF)
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
                callback.onSuccess();
            }
        });
    }
    public interface AuthManagerCallback{
        void onSuccess();
        void onFailure(String message);
    }
}
