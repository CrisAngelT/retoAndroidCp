package com.example.ecommercecineplanet.ui.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommercecineplanet.domain.provider.LoginProviderFirebase;
import com.example.ecommercecineplanet.ui.login.LoginViewModel;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private final LoginProviderFirebase loginProvider;

    public LoginViewModelFactory(LoginProviderFirebase loginProvider) {
        this.loginProvider = loginProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(loginProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
