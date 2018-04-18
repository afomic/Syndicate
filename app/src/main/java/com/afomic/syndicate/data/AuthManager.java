package com.afomic.syndicate.data;

import android.support.annotation.NonNull;

import com.afomic.syndicate.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    public void createUser(User user,String email,String password,
                           final AuthManagerCallback authManagerCallback){
        mFirebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String id=authResult.getUser().getProviderId();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                authManagerCallback.onFailure(e.getMessage());
            }
        });

    }
    public interface AuthManagerCallback{
        void onSuccess();
        void onFailure(String message);
    }
}
