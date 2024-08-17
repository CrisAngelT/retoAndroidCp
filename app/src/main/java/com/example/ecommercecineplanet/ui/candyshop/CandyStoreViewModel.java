package com.example.ecommercecineplanet.ui.candyshop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommercecineplanet.data.model.CandyStoreResponse;
import com.example.ecommercecineplanet.data.model.PremierResponse;
import com.example.ecommercecineplanet.domain.usecase.GetCandyStoreUseCase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class CandyStoreViewModel extends ViewModel {
    private final GetCandyStoreUseCase getCandyStoreUseCase;
    private final MutableLiveData<CandyStoreResponse> _candyStoreLiveData = new MutableLiveData<>();
    public LiveData<CandyStoreResponse> candyStoreLiveData() {
        return _candyStoreLiveData;
    }
    private final MutableLiveData<PremierResponse.Premiere> _candyStoreTouch = new MutableLiveData<>();
    public LiveData<PremierResponse.Premiere> candyStoreTouch() {
        return _candyStoreTouch;
    }

    public CandyStoreViewModel(GetCandyStoreUseCase getCandyStoreUseCase) {
        this.getCandyStoreUseCase = getCandyStoreUseCase;
        loadCandyStore();
    }


    private void loadCandyStore() {
        getCandyStoreUseCase.execute(new Callback<CandyStoreResponse>() {
            @Override
            public void onResponse(Call<CandyStoreResponse> call, Response<CandyStoreResponse> response) {
                if (response.isSuccessful()) {
                    _candyStoreLiveData.setValue(response.body());
                } else {
                    Timber.e("ERROR API -> %s", response.message());
                }
            }

            @Override
            public void onFailure(Call<CandyStoreResponse> call, Throwable throwable) {

            }
        });
    }


}
