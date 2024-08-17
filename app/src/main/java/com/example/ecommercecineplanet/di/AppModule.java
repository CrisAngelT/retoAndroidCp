package com.example.ecommercecineplanet.di;


import android.content.Context;


import com.example.ecommercecineplanet.commons.Constants;
import com.example.ecommercecineplanet.data.api.CinemaApi;
import com.example.ecommercecineplanet.data.repository.PremierRepositoryImpl;
import com.example.ecommercecineplanet.domain.repository.CinemaRepository;
import com.example.ecommercecineplanet.domain.usecase.GetCandyStoreUseCase;
import com.example.ecommercecineplanet.domain.usecase.GetCompleteUseCase;
import com.example.ecommercecineplanet.domain.usecase.GetPremiersUseCase;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;


public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    public CinemaApi provideApiService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(CinemaApi.class);
    }

    public CinemaRepository providePremierRepository(CinemaApi apiService) {
        return new PremierRepositoryImpl(apiService);
    }

    public GetPremiersUseCase provideGetPremiersUseCase(CinemaRepository repository) {
        return new GetPremiersUseCase(repository);
    }

    public GetCandyStoreUseCase provideGetCandyStoreUseCase(CinemaRepository repository) {
        return new GetCandyStoreUseCase(repository);
    }

    public GetCompleteUseCase provideGetCOmpletUseCase(CinemaRepository repository) {
        return new GetCompleteUseCase(repository);
    }


}
