package com.example.ecommercecineplanet.commons;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class GlobalAmountManager {

    private static GlobalAmountManager instance;

    private final MutableLiveData<Double> totalAmountLiveData;

    private GlobalAmountManager() {
        totalAmountLiveData = new MutableLiveData<>();
        totalAmountLiveData.setValue(0.0);
    }

    public static synchronized GlobalAmountManager getInstance() {
        if (instance == null) {
            instance = new GlobalAmountManager();
        }
        return instance;
    }

    public LiveData<Double> getTotalAmountLiveData() {
        return totalAmountLiveData;
    }

    public void addAmount(double amount) {
        Double currentTotal = totalAmountLiveData.getValue();
        if (currentTotal != null) {
            totalAmountLiveData.setValue(currentTotal + amount);
        }
    }
    public void reset() {
        totalAmountLiveData.setValue(0.0);
    }
    public void subtractAmount(double amount) {
        Double currentTotal = totalAmountLiveData.getValue();
        if (currentTotal != null) {
            totalAmountLiveData.setValue(currentTotal - amount);
        }
    }
}


