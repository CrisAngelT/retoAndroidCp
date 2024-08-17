package com.example.ecommercecineplanet.domain.repository;

import com.example.ecommercecineplanet.data.model.CandyStoreResponse;
import com.example.ecommercecineplanet.data.model.CompleteResponse;
import com.example.ecommercecineplanet.data.model.PremierResponse;
import com.example.ecommercecineplanet.data.model.request.CompleteRequest;

import retrofit2.Callback;

public interface CinemaRepository {

    void getPremieres(Callback<PremierResponse> callback);
    void getCandyStore(Callback<CandyStoreResponse> callback);
    void getComplete(CompleteRequest request, Callback<CompleteResponse> callback);

}
