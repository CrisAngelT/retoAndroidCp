package com.example.ecommercecineplanet.ui.pay;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommercecineplanet.data.model.CompleteResponse;
import com.example.ecommercecineplanet.data.model.PremierResponse;
import com.example.ecommercecineplanet.data.model.request.CompleteRequest;
import com.example.ecommercecineplanet.domain.usecase.GetCompleteUseCase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class PayViewModel extends ViewModel {

    private final GetCompleteUseCase getCompleteUseCase;
    private final MutableLiveData<CompleteResponse> _completeResponseLiveData = new MutableLiveData<>();
    public LiveData<CompleteResponse> completeResponseLiveData() {
        return _completeResponseLiveData;
    }

    public PayViewModel(GetCompleteUseCase getCompleteUseCase) {
        this.getCompleteUseCase = getCompleteUseCase;
    }


    public void sendCompleteCard(CompleteRequest request){
        getCompleteUseCase.execute(request,new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<CompleteResponse> call, @NonNull Response<CompleteResponse> response) {
                if (response.isSuccessful()) {
                    _completeResponseLiveData.setValue(response.body());
                } else {
                    _completeResponseLiveData.setValue(null);
                    Timber.e("ERROR API -> %s", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CompleteResponse> call, @NonNull Throwable throwable) {
                _completeResponseLiveData.setValue(null);

            }
        });
    }
}
