package com.example.ecommercecineplanet.domain.provider;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginProviderFirebase {

    private final FirebaseAuth auth;

    public LoginProviderFirebase() {
        this.auth = FirebaseAuth.getInstance();
    }


    public String getEmail() {
        FirebaseUser currentUser = auth.getCurrentUser();
        return (currentUser != null) ? currentUser.getEmail() : "";
    }
    public void logout() {
        auth.signOut();
    }

    public Task<AuthResult> login(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    public boolean existSession() {
        return auth.getCurrentUser() != null;
    }
}
