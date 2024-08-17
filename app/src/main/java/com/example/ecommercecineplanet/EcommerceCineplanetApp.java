package com.example.ecommercecineplanet;

import android.app.Application;


import com.example.ecommercecineplanet.di.AppModule;

import timber.log.Timber;

public class EcommerceCineplanetApp  extends Application {
    private AppModule appModule;
    @Override
    public void onCreate() {
        super.onCreate();

        appModule = new AppModule(this);
        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());


        }
    }

    public AppModule getAppModule() {
        return appModule;
    }
}
