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
    }

    public AppModule getAppModule() {
        return appModule;
    }
}
