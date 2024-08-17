package com.example.ecommercecineplanet.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommercecineplanet.data.model.PremierResponse;
import com.example.ecommercecineplanet.domain.usecase.GetPremiersUseCase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class HomeViewModel extends ViewModel {
    private final GetPremiersUseCase getPremiersUseCase;
    private final MutableLiveData<PremierResponse> premierResponseMutable = new MutableLiveData<>();
    public LiveData<PremierResponse> premierResponseLiveData() {
        return premierResponseMutable;
    }
    private final MutableLiveData<PremierResponse.Premiere> touchPremiereMutable = new MutableLiveData<>();
    public LiveData<PremierResponse.Premiere> touchPremiereLiveData() {
        return touchPremiereMutable;
    }

    public HomeViewModel(GetPremiersUseCase getPremiersUseCase) {
        this.getPremiersUseCase = getPremiersUseCase;
        loadPremieres();
    }


    public void loadPremieres() {
        getPremiersUseCase.execute(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<PremierResponse> call, @NonNull Response<PremierResponse> response) {
                if (response.isSuccessful()) {
                    premierResponseMutable.setValue(response.body());
                } else {
                    premierResponseMutable.setValue(null);
                    Timber.e("ERROR API -> %s", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PremierResponse> call, @NonNull Throwable throwable) {
                premierResponseMutable.setValue(null);
                Timber.e("ERROR API -> %s", throwable.getMessage());

            }
        });
    }

    public void onTouchPremier(PremierResponse.Premiere item) {
        touchPremiereMutable.setValue(item);
    }
}
