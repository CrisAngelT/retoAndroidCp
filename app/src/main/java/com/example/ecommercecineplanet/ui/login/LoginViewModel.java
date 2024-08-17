package com.example.ecommercecineplanet.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommercecineplanet.domain.provider.LoginProviderFirebase;

import timber.log.Timber;


public class LoginViewModel extends ViewModel {

    private LoginProviderFirebase loginProvider;
    private MutableLiveData<Boolean> _loginSuccess = new MutableLiveData<>();

    public LoginViewModel(LoginProviderFirebase loginProvider) {
        this.loginProvider = loginProvider;
    }


    public MutableLiveData<Boolean> loginSuccess() {
        return _loginSuccess;
    }
    public void login(String email, String password) {
        try {
            loginProvider.login(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    _loginSuccess.setValue(true);
                } else {
                    _loginSuccess.setValue(false);
                }
            }).addOnFailureListener(e -> {
                Timber.e("EXCEPTION111 $ -> %s", e.getMessage());
            });
        }
        catch (Exception e){
            Timber.e("EXCEPTION222 $ -> %s", e.getMessage());

            e.printStackTrace();
        }

    }

}
