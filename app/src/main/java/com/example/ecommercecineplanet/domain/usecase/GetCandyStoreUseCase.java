package com.example.ecommercecineplanet.domain.usecase;

import com.example.ecommercecineplanet.data.model.CandyStoreResponse;
import com.example.ecommercecineplanet.domain.repository.CinemaRepository;

import retrofit2.Callback;

public class GetCandyStoreUseCase {

    private final CinemaRepository repository;

    public GetCandyStoreUseCase(CinemaRepository repository) {
        this.repository = repository;
    }

    public void execute(Callback<CandyStoreResponse> callback) {
        repository.getCandyStore(callback);
    }
}
