package com.example.ecommercecineplanet.domain.usecase;

import com.example.ecommercecineplanet.data.model.PremierResponse;
import com.example.ecommercecineplanet.domain.repository.CinemaRepository;

import retrofit2.Callback;

public class GetPremiersUseCase {

    private final CinemaRepository repository;

    public GetPremiersUseCase(CinemaRepository repository) {
        this.repository = repository;
    }

    public void execute(Callback<PremierResponse> callback) {
        repository.getPremieres(callback);
    }
}
