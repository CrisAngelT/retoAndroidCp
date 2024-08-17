package com.example.ecommercecineplanet.ui.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommercecineplanet.domain.provider.LoginProviderFirebase;
import com.example.ecommercecineplanet.domain.usecase.GetCandyStoreUseCase;
import com.example.ecommercecineplanet.domain.usecase.GetCompleteUseCase;
import com.example.ecommercecineplanet.domain.usecase.GetPremiersUseCase;
import com.example.ecommercecineplanet.ui.candyshop.CandyStoreViewModel;
import com.example.ecommercecineplanet.ui.home.HomeViewModel;
import com.example.ecommercecineplanet.ui.login.LoginViewModel;
import com.example.ecommercecineplanet.ui.pay.PayViewModel;

public class BaseViewModel implements ViewModelProvider.Factory {
    private final GetPremiersUseCase getPremiersUseCase;
    private final GetCandyStoreUseCase getCandyStoreUseCase;
    private final GetCompleteUseCase getCompleteUseCase;
    private final LoginProviderFirebase loginProvider;

    public BaseViewModel(GetPremiersUseCase getPremiersUseCase, GetCandyStoreUseCase
            getCandyStoreUseCase, GetCompleteUseCase getCompleteUseCase,
                         LoginProviderFirebase loginProvider) {
        this.getPremiersUseCase = getPremiersUseCase;
        this.getCandyStoreUseCase = getCandyStoreUseCase;
        this.getCompleteUseCase = getCompleteUseCase;
        this.loginProvider = loginProvider;
    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)){
            return (T) new HomeViewModel(getPremiersUseCase);
        } else if (modelClass.isAssignableFrom(CandyStoreViewModel.class)){
            return (T) new CandyStoreViewModel(getCandyStoreUseCase);
        }
        else if (modelClass.isAssignableFrom(PayViewModel.class)){
            return (T) new PayViewModel(getCompleteUseCase);
        }
        else  if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(loginProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

