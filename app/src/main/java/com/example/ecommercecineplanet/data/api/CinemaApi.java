package com.example.ecommercecineplanet.data.api;

import com.example.ecommercecineplanet.data.model.CandyStoreResponse;
import com.example.ecommercecineplanet.data.model.CompleteResponse;
import com.example.ecommercecineplanet.data.model.PremierResponse;
import com.example.ecommercecineplanet.data.model.request.CompleteRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CinemaApi {

    @GET("v1/premieres")
    Call<PremierResponse> getPremieres();
    @GET("v1/candystore")
    Call<CandyStoreResponse> getCandyStore();
    @POST("v1/complete")
    Call<CompleteResponse> getComplete(@Body CompleteRequest request);
}
