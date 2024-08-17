package com.example.ecommercecineplanet.domain.usecase;

import com.example.ecommercecineplanet.data.model.CompleteResponse;
import com.example.ecommercecineplanet.data.model.request.CompleteRequest;
import com.example.ecommercecineplanet.domain.repository.CinemaRepository;

import retrofit2.Callback;

public class GetCompleteUseCase {

    private final CinemaRepository repository;

    public GetCompleteUseCase(CinemaRepository repository) {
        this.repository = repository;
    }

    public void execute(CompleteRequest request, Callback<CompleteResponse> callback) {
        repository.getComplete(request,callback);
    }
}
