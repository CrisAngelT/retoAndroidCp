package com.example.ecommercecineplanet.ui.splash;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommercecineplanet.commons.TimeHelper;
import com.example.ecommercecineplanet.databinding.ActivitySplashBinding;
import com.example.ecommercecineplanet.domain.provider.LoginProviderFirebase;
import com.example.ecommercecineplanet.ui.home.HomeAct;

import timber.log.Timber;

public class SplashAct extends AppCompatActivity {
    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TimeHelper.startTimer(this, HomeAct.class, 2000);
    }
}