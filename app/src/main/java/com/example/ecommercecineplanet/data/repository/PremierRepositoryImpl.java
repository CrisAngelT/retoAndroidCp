package com.example.ecommercecineplanet.data.repository;

import com.example.ecommercecineplanet.data.api.CinemaApi;
import com.example.ecommercecineplanet.data.model.CandyStoreResponse;
import com.example.ecommercecineplanet.data.model.CompleteResponse;
import com.example.ecommercecineplanet.data.model.PremierResponse;
import com.example.ecommercecineplanet.data.model.request.CompleteRequest;
import com.example.ecommercecineplanet.domain.repository.CinemaRepository;

import retrofit2.Call;
import retrofit2.Callback;

public class PremierRepositoryImpl implements CinemaRepository {
    private final CinemaApi apiService;

    public PremierRepositoryImpl(CinemaApi apiService) {
        this.apiService = apiService;
    }


    @Override
    public void getPremieres(Callback<PremierResponse> callback) {
        Call<PremierResponse> call = apiService.getPremieres();
        call.enqueue(callback);
    }

    @Override
    public void getCandyStore(Callback<CandyStoreResponse> callback) {
        Call<CandyStoreResponse> call = apiService.getCandyStore();
        call.enqueue(callback);
    }

    @Override
    public void getComplete(CompleteRequest request, Callback<CompleteResponse> callback) {
        Call<CompleteResponse> call = apiService.getComplete(request);
        call.enqueue(callback);
    }
}
